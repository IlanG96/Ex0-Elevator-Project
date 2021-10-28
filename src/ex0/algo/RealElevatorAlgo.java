package ex0.algo;

import ex0.Building;
import ex0.CallForElevator;
import ex0.Elevator;



/*
קובץ שינויים בזמנים
https://docs.google.com/spreadsheets/d/15xdKg54MZlS8HdEOpl2ack6j4OCe1mMRHgPfU8PNEQ0/edit?usp=sharing */
public class RealElevatorAlgo implements ElevatorAlgo {
    public static final int UP = 1, DOWN = -1, LEVEL = 0 ,ERROR=-2;
    private int _direction;
    private MaxHeap[] DownQueue;
    private MinHeap[] UpQueue;
    private Building _building;
    private boolean firstSend;
    private static int counterUp=0;
    boolean send=true;

    public RealElevatorAlgo(Building Q) {
        _building = Q;
        _direction = UP;
        DownQueue = new MaxHeap[_building.numberOfElevetors()];
        UpQueue = new MinHeap[_building.numberOfElevetors()];
        for (int i = 0; i < UpQueue.length; i++) {
            UpQueue[i] = new MinHeap();
            DownQueue[i] = new MaxHeap();
            firstSend = true;
        }
    }

    @Override
    public Building getBuilding() {
        return _building;
    }

    @Override
    public String algoName() {
        return "Ex0_OOP_Advance_Elevator_Algo";
    }

    /**
     * allocate an elevator according to the closest elevator in the same direction
     **/
    @Override
    public int allocateAnElevator(CallForElevator c) {
        int choosenElev = 0 ,elevator=0 ,elevnum = _building.numberOfElevetors();
        if (elevnum >= 1) {
            if (c.getSrc() < c.getDest()) {//UP(if the call source smaller then call dest)
                choosenElev = CloseElevUp(c.getSrc());
                Elevator d = _building.getElevetor(choosenElev);
                if (UpQueue[choosenElev].getSize() > 0) {
                    if (c.getSrc() < UpQueue[choosenElev].peek() && c.getSrc() >= d.getPos()) {
                        // d.goTo(c.getDest()); // SOME TEST BECOME FASTER BUT WITH MORE UNCOMPLETED CALLS
                        d.stop(c.getSrc());
                    }
                }
                UpQueue[choosenElev].insert(c.getSrc());
                UpQueue[choosenElev].insert(c.getDest());

            } else { //down(if the call source bigger then dest source)
                choosenElev = CloseElevDown(c.getSrc());
                Elevator d = _building.getElevetor(choosenElev);
                if (DownQueue[choosenElev].getSize() > 0) {
                    if (c.getSrc() > DownQueue[choosenElev].peek()&&c.getSrc()<d.getPos()) {
                        d.stop(c.getSrc());}
                }
                DownQueue[choosenElev].insert(c.getSrc());
                DownQueue[choosenElev].insert(c.getDest());
            }
        }
        return choosenElev;

    }


    /**
     * Returns the closest Elavator to the UP call in the same direction or at the LEVEL status .
     */
    private int CloseElevUp(int src) {
        int elevClose = _building.numberOfElevetors();
        int x = 0, finalx = 0;
        double xspeed=0,finalxspeed=0,min = Integer.MAX_VALUE;
        while (x < elevClose) {
            Elevator check = this._building.getElevetor(x);
            if(check.getState()!=ERROR) {
                if (check.getState() == UP && DownQueue[x].getSize() == 0) { //need to add level //DELETE DOWNQUEUE SIZE AND SOME TESTS WILL BE FASTER
                    if ((dist(src, x) <= min)) {
                        finalx = x;
                        min = dist(src, x);
                    }
                } else if (check.getState() == LEVEL) {
                    xspeed = check.getSpeed();
                    if ((dist(src, x) <= min) && (finalxspeed < xspeed)) {
                        min = dist(src, x);
                        finalx = x;
                        finalxspeed = xspeed;
                    }
                }
            }
            x++;
        }
        return finalx;
    }

    /**
     * Returns the closest Elavator to the Down call in the same direction or at the LEVEL status .
     */
    private int CloseElevDown(int src) {
        int elevClose = _building.numberOfElevetors();
        int x = 0, finalx = 0;
        double xspeed=0,finalxspeed=0,min = Integer.MAX_VALUE;
        while (x < elevClose) {
            Elevator check = this._building.getElevetor(x);
            if(check.getState()!=ERROR) {
                if (check.getState() == DOWN && UpQueue[x].getSize() == 0) {//need to add level
                    if ((dist(src, x) < min)) {
                        min = dist(src, x);
                        finalx = x;
                    }
                } else if (check.getState() == LEVEL) {//{&& UpQueue[x].getSize() == 0) {
                    if ((dist(src, x) <= min)) {
                        min = dist(src, x);
                        finalx = x;
                    }
                }
            }
            x++;
        }
        return finalx;
    }

    /**
     * Check the distance of an elevator for the src floor
     * @param src the wanted floor
     * @param elev the selected elevator
     * @return ans-The distance
     */
    private double dist(int src, int elev) {
        double ans = -1;
        Elevator thisElev = this._building.getElevetor(elev);
        int countStops=count(elev);
        int pos = thisElev.getPos();
        double start= thisElev.getStartTime();
        double stop= thisElev.getStopTime();
        double close=thisElev.getTimeForClose();
        double open= thisElev.getTimeForOpen();
        double rideTime=thisElev.getSpeed()+stop+start+close+open;
        if(thisElev.getState()==LEVEL){
            ans =(Math.abs(pos-src))/rideTime;
        }
        else {
            ans = ((Math.abs(pos-src))+countStops)/rideTime;
        }
        return ans;
    }

    /**
     * This function count the number of calls for the selected elevator
     * @param elev - The elevator that the function count the calls for.
     * @return the numbers of calls for the elevator
     */
    private int count(int elev){
        int number=0;
        number=DownQueue[elev].getSize()+UpQueue[elev].getSize();
        return number;
    }

    public int getDirection() {
        return this._direction;
    }

    /**
     *This function send the 2 slowest elevator to the max floor to help with the higher level calls
     */
    private void More_Then_Six_Send(){
        int numOfElev = _building.numberOfElevetors();
        if (firstSend == true) {
            int min = this._building.minFloor(), max = this._building.maxFloor();
            Elevator curr2;
            double minspeed = this.getBuilding().getElevetor(0).getSpeed();
            double minspeed2 = minspeed;
            int x = 0, z = 0,y=0;
            for (int i = 1; i < numOfElev; i++) {
                curr2 = this.getBuilding().getElevetor(i);
                double curr2speed = curr2.getSpeed();
                if (curr2speed <= minspeed) {
                    minspeed = curr2speed;
                    z=x;
                    x = i;
                }
                firstSend = false;
            }
            this.getBuilding().getElevetor(x).goTo(max);
            //this.getBuilding().getElevetor(x).stop(max);
            this.getBuilding().getElevetor(z).goTo(max);
            //this.getBuilding().getElevetor(z).stop(max);
        }
    }

    /**
     * This function send the slowest elevator to the max floor to help with the higher level calls
     */
    private void ThreeToSixSend(){
        int numOfElev = _building.numberOfElevetors();
        if (firstSend == true) { //send elevators to max floor
            int min = this._building.minFloor(), max = this._building.maxFloor();
            int split = numOfElev / 3;
            Elevator curr2;
            double minspeed = this.getBuilding().getElevetor(0).getSpeed();
            int x = 0;
            for (int i = 1; i < numOfElev; i++) {
                curr2 = this.getBuilding().getElevetor(i);
                double curr2speed = curr2.getSpeed();
                if (curr2speed < minspeed) {
                    minspeed = curr2speed;
                    x = i;
                }
                firstSend = false;
            }
            this.getBuilding().getElevetor(x).goTo(max);
        }
    }
    @Override
    public void cmdElevator(int elev) {
        int numOfElev = _building.numberOfElevetors();
        Elevator curr = this.getBuilding().getElevetor(elev);
        int dir = this.getDirection();
        numOfElev = _building.numberOfElevetors();
        if (numOfElev >= 3 && numOfElev < 6) {
            ThreeToSixSend();
        } else if (numOfElev >= 6) {
            More_Then_Six_Send();
        }
        //In case there's no calls
        if (UpQueue[elev].getSize() == 0 && DownQueue[elev].getSize() == 0) {
            //curr.goTo(0);
        }
        //In case Theres UP calls and the elevator in the up direction
        else if (curr.getState() == UP && UpQueue[elev].getSize() != 0) {
            if (curr.getPos() != UpQueue[elev].peek()) {// if the elevator move up and not at source
                curr.goTo(UpQueue[elev].peek());
            } else {
                UpQueue[elev].deleteMin();
                if (UpQueue[elev].getSize() > 0)
                    curr.goTo(UpQueue[elev].peek());
            }
        }
        //In case theres down calls and the elevator in down direction
        else if (curr.getState() == DOWN && DownQueue[elev].getSize() != 0) {
            if (curr.getPos() != DownQueue[elev].peek()) //if the elevator move down and not at source
                curr.goTo(DownQueue[elev].peek());
            else {
                DownQueue[elev].deleteMax();
                if (DownQueue[elev].getSize() > 0)
                    curr.goTo(DownQueue[elev].peek());
            }
        }
        //In case the elevator in Level Status check where there's more calls Down or UP
        else {
            if (UpQueue[elev].getSize() >= DownQueue[elev].getSize()) {
                if (curr.getPos() != UpQueue[elev].peek())
                    curr.goTo(UpQueue[elev].peek());
                else {
                    UpQueue[elev].deleteMin();
                    if (UpQueue[elev].getSize() > 0)
                        curr.goTo(UpQueue[elev].peek());
                }
            } else {
                if (curr.getPos() != DownQueue[elev].peek())
                    curr.goTo(DownQueue[elev].peek());
                else {
                    DownQueue[elev].deleteMax();
                    if (DownQueue[elev].getSize() > 0)
                        curr.goTo(DownQueue[elev].peek());
                }
            }
        }
    }




}