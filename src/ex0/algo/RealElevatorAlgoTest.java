package ex0.algo;

import ex0.Building;
import ex0.CallForElevator;
import ex0.Elevator;
import ex0.simulator.Call_A;
import ex0.simulator.ElevetorCallList;
import ex0.simulator.Simulator_A;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class RealElevatorAlgoTest {


   private Building B1;
    private Building B4;
    private Building B9;
    private  RealElevatorAlgo MyAlgo1;
    private  RealElevatorAlgo MyAlgo4;
    private  RealElevatorAlgo MyAlgo9;
    private ElevetorCallList c;
    private Call_A call;
    private Elevator[] ElevatorList;

    public RealElevatorAlgoTest(){

        Simulator_A.initData(1,null);
        B1 = Simulator_A.getBuilding();
        Simulator_A.initData(9,null);
        B9 = Simulator_A.getBuilding();
        c=new ElevetorCallList();
        call=new Call_A(1,2,3);
        Call_A call2=new Call_A(-1,10,2);
        Call_A call3=new Call_A(1,-1,20);
        Call_A call4=new Call_A(1,5,9);
        Call_A call5=new Call_A(-1,100,-2);
        Call_A call6=new Call_A(-1,5,4);
        Call_A call7=new Call_A(1,-2,4);
        Call_A call8=new Call_A(-1,6,1);
        c.add(call2);
        c.add(call);
        c.add(call3);
        c.add(call4);
        c.add(call5);
        c.add(call6);
        c.add(call7);
        c.add(call8);
        MyAlgo1 = new RealElevatorAlgo(B1);
        Simulator_A.initAlgo(MyAlgo1);
        MyAlgo9 =new RealElevatorAlgo(B9);
        ElevatorList=new Elevator[B1.numberOfElevetors()];
        for (int i = 0; i < ElevatorList.length; i++) {
            ElevatorList[i]=MyAlgo1.getBuilding().getElevetor(i);
        }


    }

    @Test
    void allocateAnElevator() {
        Simulator_A.initData(9,null);
        B9 = Simulator_A.getBuilding();

        MyAlgo9=new RealElevatorAlgo(B9);
        Simulator_A.initAlgo(MyAlgo9);
        ///////////CASE 1 allocate TEST///////////////////////
        int elev=MyAlgo1.allocateAnElevator(c.get(0));
        assertEquals(elev,0);

      ///////////CASE 9 allocate TEST///////////////////////
        //First Test start with 0 Calls for all the elevator//
        //////Allocate The fastest elevator according to speed and calls////
        int call0= MyAlgo9.allocateAnElevator(c.get(0));
       int call1= MyAlgo9.allocateAnElevator(c.get(1));
        int call2= MyAlgo9.allocateAnElevator(c.get(2));
        int call3= MyAlgo9.allocateAnElevator(c.get(3));
        int call4= MyAlgo9.allocateAnElevator(c.get(4));
        int call5= MyAlgo9.allocateAnElevator(c.get(5));
        int call6= MyAlgo9.allocateAnElevator(c.get(6));
        int call7= MyAlgo9.allocateAnElevator(c.get(7));
        assertEquals(call0,3);
        assertEquals(call1,1);
        assertEquals(call2,3);
        assertEquals(call3,1);
        assertEquals(call4,3);
        assertEquals(call5,1);
        assertEquals(call6,0);
        assertEquals(call7,3);
        ////////Case 9 Test with EX0_Simulator
        //allocate the best elevator when the queue is still with the uncompleted calls for the simulator////////
        MyAlgo9=new RealElevatorAlgo(B9);
        Simulator_A.initAlgo(MyAlgo9);
        Simulator_A.runSim();
        call0= MyAlgo9.allocateAnElevator(c.get(0));
        call1= MyAlgo9.allocateAnElevator(c.get(1));
        call2= MyAlgo9.allocateAnElevator(c.get(2));
        call3= MyAlgo9.allocateAnElevator(c.get(3));
        call4= MyAlgo9.allocateAnElevator(c.get(4));
        call5= MyAlgo9.allocateAnElevator(c.get(5));
        call6= MyAlgo9.allocateAnElevator(c.get(6));
        call7= MyAlgo9.allocateAnElevator(c.get(7));
        assertEquals(call0,6);
        assertEquals(call1,6);
        assertEquals(call2,6);
        assertEquals(call3,6);
        assertEquals(call4,0);
        assertEquals(call5,2);
        assertEquals(call6,7);
        assertEquals(call7,2);
///////////////Case 4 Allocate Test//////////////////////
        Simulator_A.initData(4,null);
        B4=Simulator_A.getBuilding();
        MyAlgo4=new RealElevatorAlgo(B4);
        //First Test start with 0 Calls for all the elevator//
        //////Allocate The fastest elevator according to speed and calls////
         call0= MyAlgo4.allocateAnElevator(c.get(0));
         call1= MyAlgo4.allocateAnElevator(c.get(1));
         call2= MyAlgo4.allocateAnElevator(c.get(2));
         call3= MyAlgo4.allocateAnElevator(c.get(3));
         call4= MyAlgo4.allocateAnElevator(c.get(4));
         call5= MyAlgo4.allocateAnElevator(c.get(5));
         call6= MyAlgo4.allocateAnElevator(c.get(6));
         call7= MyAlgo4.allocateAnElevator(c.get(7));
        assertEquals(call0,2);
        assertEquals(call1,1);
        assertEquals(call2,0);
        assertEquals(call3,2);
        assertEquals(call4,2);
        assertEquals(call5,3);
        assertEquals(call6,1);
        assertEquals(call7,3);
        ////////Case 9 Test with EX0_Simulator
        //allocate the best elevator when the queue is still with the uncompleted calls for the simulator////////
        MyAlgo4=new RealElevatorAlgo(B4);
        Simulator_A.initAlgo(MyAlgo4);
        Simulator_A.runSim();
        call0= MyAlgo4.allocateAnElevator(c.get(0));
        call1= MyAlgo4.allocateAnElevator(c.get(1));
        call2= MyAlgo4.allocateAnElevator(c.get(2));
        call3= MyAlgo4.allocateAnElevator(c.get(3));
        call4= MyAlgo4.allocateAnElevator(c.get(4));
        call5= MyAlgo4.allocateAnElevator(c.get(5));
        call6= MyAlgo4.allocateAnElevator(c.get(6));
        call7= MyAlgo4.allocateAnElevator(c.get(7));
        assertEquals(call0,2);
        assertEquals(call1,2);
        assertEquals(call2,2);
        assertEquals(call3,2);
        assertEquals(call4,2);
        assertEquals(call5,3);
        assertEquals(call6,2);
        assertEquals(call7,3);
    }

    @Test
    void cmdElevator() {
        B4=Simulator_A.getBuilding();
        MyAlgo4=new RealElevatorAlgo(B4);
        Simulator_A.initAlgo(MyAlgo4);
        MyAlgo4.getBuilding().getElevetor(0).goTo(4);
        MyAlgo4.getBuilding().getElevetor(2).goTo(10);
        Simulator_A.runSim();
        //assertEquals(4,MyAlgo4.getBuilding().getElevetor(0).getPos());
        MyAlgo4.getBuilding().getElevetor(2).goTo(10);
        assertEquals(10,MyAlgo4.getBuilding().getElevetor(2).getPos());

    }
    }