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
    private Building B9;
    private  RealElevatorAlgo MyAlgo1;
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
        c.add(call);
        MyAlgo1 = new RealElevatorAlgo(B1);
        MyAlgo9 =new RealElevatorAlgo(B9);
        ElevatorList=new Elevator[B1.numberOfElevetors()];
        for (int i = 0; i < ElevatorList.length; i++) {
            ElevatorList[i]=MyAlgo1.getBuilding().getElevetor(i);
        }


    }

    @Test
    void allocateAnElevator() {
        int elev=MyAlgo1.allocateAnElevator(c.get(0));
        assertEquals(elev,0);




    }

    @Test
    void cmdElevator() {
        MyAlgo1.allocateAnElevator(c.get(0));
        MyAlgo1.cmdElevator(0);
        for (int i = 0; i <ElevatorList.length ; i++) {
            assertEquals(1,ElevatorList[i].getState());
        }



    }
    }