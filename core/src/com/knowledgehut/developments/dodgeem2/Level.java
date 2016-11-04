package com.knowledgehut.developments.dodgeem2;


public class Level {

    private int level_number;
    private int fruits, cherry, strawberry, orange, galaxian, pellet;
    private int score, enemy;

    public Level(int level_number){
        this.level_number = level_number;
        fruits = cherry = strawberry = orange = galaxian = pellet = score = 0;
        enemy = 0;
        levelCreator();

    }

    private void levelCreator(){
        switch(level_number){
            case 1: fruits = 4; break;
            case 2: orange = 5; break;
            case 3: score = 3000; break;
            case 4: cherry = 6; break;
            case 5: fruits = 10; break;
            case 6: score = 5000; break;
            case 7: enemy = 4; break;
            case 8: strawberry = 10; break;
            case 9: fruits = 15; break;
            case 10: cherry = 10; break;
            case 11: score = 6000; break;
            case 12: enemy = 10; break;
            case 13: orange = 15; cherry = 5; break;
            case 14: cherry = 15; orange = 5; break;
            case 15: score = 7500; break;
            case 16: enemy = 15; break;
            case 17: fruits = 15; pellet = 2; break;
            case 18: score = 10000; cherry = 7; break;
            case 19: enemy = 20; break;
            case 20: pellet = 10; break;
            case 21: fruits = 20; break;
            case 22: cherry = 5; strawberry = 5; orange = 5; enemy = 15; break;
            case 23: score = 13000; break;
            case 24: cherry = 15; strawberry = 15; orange = 15; break;
            case 25: score = 13000; cherry = 10; strawberry = 10; orange = 10; break;
            case 26: enemy = 25; break;
            case 27: enemy = 26; break;
            case 28: score = 20000; break;
            case 29: cherry = 20; pellet = 5; break;
            case 30: orange = 20; break;
            case 31: strawberry = 20; break;
            case 32: score = 22000; break;
            case 33: score = 25000; break;
            case 34: enemy = 30; break;
            case 35: galaxian = 2; break;
            case 36: enemy = 10; cherry = 10; strawberry = 10; break;
            case 37: enemy = 15; strawberry = 10; break;
            case 38: enemy = 20; orange = 10; break;
            case 39: galaxian = 4; break;
            case 40: fruits = 30; break;
            case 41: enemy = 35; break;
            case 42: cherry = 20; strawberry = 20; enemy = 20; break;
            case 43: strawberry = 30; break;
            case 44: score = 33000; enemy = 10; break;
            case 45: score = 40000; break;
            case 46: enemy = 40; break;
            case 47: orange = 40; break;
            case 48: fruits = 45; break;
            case 49: galaxian = 3; strawberry = 20; pellet = 5; break;
            case 50: score = 45000; enemy = 25; break;
            case 51: enemy = 50; break;
            case 52: fruits = 55; pellet = 15; break;
            case 53: fruits = 60; break;
            case 54: score = 50000; break;
            case 55: fruits = 40; pellet = 20; galaxian = 4; break;
            case 56: strawberry = 30; orange = 30; cherry = 30; break;
            case 57: pellet = 15; galaxian = 4; enemy = 25; break;
            case 58: enemy = 30; fruits = 40; galaxian = 5; pellet = 7; break;
            case 59: score = 60000; pellet = 20; break;
            case 60: score = 100000; galaxian = 8; break;
        }
    }

    public void setLevel(int level){
        this.level_number = level;
    }


    public boolean Success(){
        boolean success = false;
        switch(level_number){
            case 1:
                if(DodgeEm2.FRUIT_SCORE >= fruits) success = true; break;
            case 2:
                if(DodgeEm2.ORANGE_SCORE >= orange) success = true; break;
            case 3:
                if(DodgeEm2.SCORE >= score) success = true; break;
            case 4:
                if(DodgeEm2.CHERRY_SCORE >= cherry) success = true; break;
            case 5:
                if(DodgeEm2.FRUIT_SCORE >= fruits) success = true; break;
            case 6:
                if(DodgeEm2.SCORE >= score) success = true; break;
            case 7:
                if(DodgeEm2.ENEMIES_KILLED >= enemy) success = true; break;
            case 8:
                if(DodgeEm2.STRAWBERRY_SCORE >= strawberry) success = true; break;
            case 59:
                if(DodgeEm2.SCORE >= score && DodgeEm2.PELLET_SCORE >= pellet) success = true; break;
            case 60:
                if(DodgeEm2.SCORE >= score && DodgeEm2.GALAXIAN_SCORE >= galaxian) success = true; break;
        }
        return success;
    }




}
