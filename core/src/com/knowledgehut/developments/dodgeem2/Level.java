package com.knowledgehut.developments.dodgeem2;


import java.util.ArrayList;
import java.util.HashMap;

public class Level {

    private int level_number;
    private int fruits, cherry, strawberry, orange, galaxian;
    private int score, enemy;

    public Level(int level_number){
        this.level_number = level_number;
        fruits = cherry = strawberry = orange = galaxian = score = 0;
        enemy = 0;
        levelCreator();

    }

    public HashMap<String, Integer> getLevelData(){
        HashMap<String, Integer>levelData = new HashMap<String, Integer>();

        if(fruits > 0) levelData.put("fruit", fruits);
        if(cherry > 0) levelData.put("cherry", cherry);
        if(strawberry > 0) levelData.put("strawberry", strawberry);
        if(orange > 0) levelData.put("orange", orange);
        if(galaxian > 0) levelData.put("galaxian", galaxian);
        if(score > 0) levelData.put("score", score);
        if(enemy > 0) levelData.put("enemy", enemy);

        return levelData;
    }

    private void levelCreator(){
        switch(level_number){
            case 1: fruits = 4; break;
            case 2: orange = 2; break;
            case 3: score = 3000; break;
            case 4: cherry = 3; break;
            case 5: fruits = 6; break;
            case 6: score = 5000; break;
            case 7: enemy = 1; break;
            case 8: strawberry = 4; break;
            case 9: fruits = 8; break;
            case 10: cherry = 4; break;
            case 11: score = 6000; break;
            case 12: enemy = 4; break;
            case 13: orange = 3; cherry = 5; break;
            case 14: cherry = 4; orange = 5; break;
            case 15: score = 7500; break;
            case 16: enemy = 6; break;
            case 17: fruits = 7; enemy = 5; break;
            case 18: score = 10000; cherry = 7; break;
            case 19: enemy = 10; break;
            case 20: orange = 6; enemy = 8; break;
            case 21: fruits = 11; break;
            case 22: score = 13000; break;//cherry = 5; strawberry = 5; orange = 5; enemy = 15; break;
            case 23: cherry = 6; strawberry = 8; break;//score = 13000; break;
            case 24: cherry = 8; strawberry = 8; orange = 8; break;
            case 25: score = 18000; break;// cherry = 10; strawberry = 10; orange = 10; break;
            case 26: enemy = 12; cherry = 2; break;
            case 27: enemy = 14; break;
            case 28: score = 20000; break;
            case 29: strawberry = 10; galaxian = 1; break;
            case 30: orange = 9; break;
            case 31: strawberry = 15; break;
            case 32: score = 22000; break;
            case 33: score = 25000; break;
            case 34: enemy = 18; break;
            case 35: galaxian = 2; break;
            case 36: enemy = 10; cherry = 10; strawberry = 10; break;
            case 37: enemy = 15; strawberry = 10; break;
            case 38: enemy = 20; orange = 10; break;
            case 39: galaxian = 4; break;
            case 40: fruits = 20; galaxian = 2; break;
            case 41: enemy = 23; break;
            case 42: cherry = 14; strawberry = 10; orange = 6; break;
            case 43: strawberry = 16; break;
            case 44: score = 33000; enemy = 10; break;
            case 45: score = 40000; break;
            case 46: enemy = 28; break;
            case 47: orange = 15; break;
            case 48: fruits = 26; break;
            case 49: galaxian = 4; strawberry = 15; cherry = 18; break;
            case 50: score = 45000; enemy = 25; break;
            case 51: enemy = 30; break;
            case 52: fruits = 30; enemy = 25; galaxian = 3; break;
            case 53: fruits = 40; break;
            case 54: score = 65000; break;
            case 55: fruits = 50; enemy = 30; galaxian = 4; break;
            case 56: strawberry = 20; enemy = 40; cherry = 24; break;
            case 57: cherry = 35; galaxian = 4; enemy = 25; break;
            case 58: enemy = 60; fruits = 60; galaxian = 6; break;
            case 59: score = 100000; enemy = 70; fruits = 55; break;
            case 60: score = 250000; galaxian = 10; orange = 50; break;
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
            case 9:
                if(DodgeEm2.FRUIT_SCORE >= fruits) success = true; break;
            case 10:
                if(DodgeEm2.CHERRY_SCORE >= cherry) success = true; break;
            case 11:
                if(DodgeEm2.SCORE >= score) success = true; break;
            case 12:
                if(DodgeEm2.ENEMIES_KILLED >= enemy) success = true; break;
            case 13:
                if(DodgeEm2.CHERRY_SCORE >= cherry && DodgeEm2.ORANGE_SCORE >= orange) success = true; break;
            case 14:
                if(DodgeEm2.CHERRY_SCORE >= cherry && DodgeEm2.ORANGE_SCORE >= orange) success = true; break;
            case 15:
                if(DodgeEm2.SCORE >= score) success = true; break;
            case 16:
                if(DodgeEm2.ENEMIES_KILLED >= enemy) success = true; break;
            case 17:
                if(DodgeEm2.FRUIT_SCORE >= fruits && DodgeEm2.ENEMIES_KILLED >= 10) success = true; break;
            case 18:
                if(DodgeEm2.CHERRY_SCORE >= cherry && DodgeEm2.SCORE >= score) success = true; break;
            case 19:
                if(DodgeEm2.ENEMIES_KILLED >= enemy) success = true; break;
            case 20:
                if(DodgeEm2.ENEMIES_KILLED >= enemy && DodgeEm2.ORANGE_SCORE >= orange) success = true; break;
            case 21:
                if(DodgeEm2.FRUIT_SCORE >= fruits) success = true; break;
            case 22:
                if(DodgeEm2.ENEMIES_KILLED >= enemy && DodgeEm2.CHERRY_SCORE >= cherry &&
                    DodgeEm2.ORANGE_SCORE >= orange && DodgeEm2.STRAWBERRY_SCORE >= strawberry) success = true; break;
            case 23:
                if(DodgeEm2.SCORE >= score) success = true; break;
            case 24:
                if(DodgeEm2.CHERRY_SCORE >= cherry &&
                    DodgeEm2.ORANGE_SCORE >= orange && DodgeEm2.STRAWBERRY_SCORE >= strawberry) success = true; break;
            case 25:
                if(DodgeEm2.SCORE >= score && DodgeEm2.CHERRY_SCORE >= cherry &&
                    DodgeEm2.ORANGE_SCORE >= orange && DodgeEm2.STRAWBERRY_SCORE >= strawberry) success = true; break;
            case 26:
                if(DodgeEm2.ENEMIES_KILLED >= enemy) success = true; break;
            case 27:
                if(DodgeEm2.ENEMIES_KILLED >= enemy) success = true; break;
            case 28:
                if(DodgeEm2.SCORE >= score) success = true; break;
            case 29:
                if(DodgeEm2.CHERRY_SCORE >= cherry && DodgeEm2.GALAXIAN_SCORE >= galaxian) success = true; break;
            case 30:
                if(DodgeEm2.ORANGE_SCORE >= orange) success = true; break;
            case 31:
                if(DodgeEm2.STRAWBERRY_SCORE >= strawberry) success = true; break;
            case 32:
                if(DodgeEm2.SCORE >= score) success = true; break;
            case 33:
                if(DodgeEm2.SCORE >= score) success = true; break;
            case 34:
                if(DodgeEm2.ENEMIES_KILLED >= enemy) success = true; break;
            case 35:
                if(DodgeEm2.GALAXIAN_SCORE >= galaxian) success = true; break;
            case 36:
                if(DodgeEm2.STRAWBERRY_SCORE >= strawberry && DodgeEm2.ENEMIES_KILLED >= enemy &&
                    DodgeEm2.CHERRY_SCORE >= cherry) success = true; break;
            case 37:
                if(DodgeEm2.STRAWBERRY_SCORE >= strawberry && DodgeEm2.ENEMIES_KILLED >= enemy) success = true; break;
            case 38:
                if(DodgeEm2.ORANGE_SCORE >= orange && DodgeEm2.ENEMIES_KILLED >= enemy) success = true; break;
            case 39:
                if(DodgeEm2.GALAXIAN_SCORE >= galaxian) success = true; break;
            case 40:
                if(DodgeEm2.FRUIT_SCORE >= fruits) success = true; break;
            case 41:
                if(DodgeEm2.ENEMIES_KILLED >= enemy) success = true; break;
            case 42:
                if(DodgeEm2.STRAWBERRY_SCORE >= strawberry && DodgeEm2.ENEMIES_KILLED >= enemy &&
                    DodgeEm2.CHERRY_SCORE >= cherry) success = true; break;
            case 43:
                if(DodgeEm2.STRAWBERRY_SCORE >= strawberry) success = true; break;
            case 44:
                if(DodgeEm2.ENEMIES_KILLED >= enemy && DodgeEm2.SCORE >= score) success = true; break;
            case 45:
                if(DodgeEm2.SCORE >= score) success = true; break;
            case 46:
                if(DodgeEm2.ENEMIES_KILLED >= enemy) success = true; break;
            case 47:
                if(DodgeEm2.ORANGE_SCORE >= orange) success = true; break;
            case 48:
                if(DodgeEm2.FRUIT_SCORE >= fruits) success = true; break;
            case 49:
                if(DodgeEm2.STRAWBERRY_SCORE >= strawberry && DodgeEm2.GALAXIAN_SCORE >= galaxian
                        && DodgeEm2.ORANGE_SCORE >= orange) success = true; break;
            case 50:
                if(DodgeEm2.ENEMIES_KILLED >= enemy && DodgeEm2.SCORE >= score) success = true; break;
            case 51:
                if(DodgeEm2.ENEMIES_KILLED >= enemy) success = true; break;
            case 52:
                if(DodgeEm2.ENEMIES_KILLED >= enemy && DodgeEm2.FRUIT_SCORE >= fruits &&
                        DodgeEm2.GALAXIAN_SCORE >= galaxian) success = true; break;
            case 53:
                if(DodgeEm2.FRUIT_SCORE >= fruits) success = true; break;
            case 54:
                if(DodgeEm2.SCORE >= score) success = true; break;
            case 55:
                if(DodgeEm2.FRUIT_SCORE >= fruits && DodgeEm2.ENEMIES_KILLED >= enemy &&
                        DodgeEm2.GALAXIAN_SCORE >= galaxian) success = true; break;
            case 56:
                if(DodgeEm2.CHERRY_SCORE >= cherry &&
                    DodgeEm2.ORANGE_SCORE >= orange && DodgeEm2.STRAWBERRY_SCORE >= strawberry) success = true; break;
            case 57:
                if(DodgeEm2.ENEMIES_KILLED >= enemy && DodgeEm2.CHERRY_SCORE >= cherry &&
                        DodgeEm2.GALAXIAN_SCORE >= galaxian) success = true; break;
            case 58:
                if(DodgeEm2.FRUIT_SCORE >= fruits && DodgeEm2.ENEMIES_KILLED >= enemy &&
                    DodgeEm2.GALAXIAN_SCORE >= galaxian) success = true; break;
            case 59:
                if(DodgeEm2.SCORE >= score && DodgeEm2.FRUIT_SCORE >= fruits &&
                        DodgeEm2.ENEMIES_KILLED >= enemy) success = true; break;
            case 60:
                if(DodgeEm2.SCORE >= score && DodgeEm2.GALAXIAN_SCORE >= galaxian) success = true; break;
        }
        return success;
    }




}
