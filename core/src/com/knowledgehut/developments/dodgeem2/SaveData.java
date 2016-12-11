package com.knowledgehut.developments.dodgeem2;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.JsonWriter;

import java.io.IOException;
import java.util.*;


public class SaveData {

    public class ArcadeScores implements Comparable<ArcadeScores>{
        int level;
        String name;
        int score;

        ArcadeScores(int level, String name, int score){
            this.level = level;
            this.name = name;
            this.score = score;
        }

        public int getLevel(){
            return level;
        }

        public String getName(){
            return name;
        }

        public int getScore(){
            return score;
        }



        @Override
        public int compareTo(ArcadeScores o) {
            int c = this.getLevel() - o.getLevel();

            if(c == 0){
                c = this.getScore() - o.getScore();
            }
            return c;
        }
    }


    public class HighScores implements Comparable<HighScores>{
        String name;
        int score;
        String time;

        HighScores(String name, int score, String time){
            this.name = name;
            this.score = score;
            this.time = time;
        }

        public String getName(){
            return name;
        }

        public int getScore(){
            return score;
        }

        public String getTime() {return time;}

        @Override
        public int compareTo(HighScores other_item) {
            return this.getScore() - other_item.getScore();
        }
    }



    public class GameText{
        int level;
        ArrayList<String> myData = new ArrayList<String>();

        GameText(int level, ArrayList<String> myData){
            this.level = level;
            this.myData = myData;
        }

        public int getLevel(){
            return level;
        }

        public ArrayList<String> getGoals(){
            return myData;
        }
    }



    public GameText getGameData(FileHandle fileHandle, int level) throws Exception{
        JsonReader jsonReader = new JsonReader();
        ArrayList<String> my_data = new ArrayList<String>();

        if(fileHandle.exists()) {
            JsonValue jsonValue = jsonReader.parse(fileHandle);

            int my_level;


            for (JsonValue data : jsonValue.iterator()) {
                if (data.getInt("level") == level) {
                    my_level = data.getInt("level");
                    for (JsonValue text : data.get("info").iterator()) {
                        my_data.add(text.getString("text"));
                    }
                    return new GameText(my_level, my_data);
                }
            }
        }
        return null;
    }

    private ArrayList<HighScores> readJsonFromFile(FileHandle fileHandle) throws IOException {
        ArrayList<HighScores> leaderBoard = new ArrayList<HighScores>();
        JsonReader jsonReader = new JsonReader();

        if(fileHandle.exists()) {
            JsonValue jsonValue = jsonReader.parse(fileHandle);
            if (jsonValue != null) {
                for (JsonValue scoreboard : jsonValue.iterator()) {
                    leaderBoard.add(new HighScores(scoreboard.getString("name"), scoreboard.getInt("score"),
                            scoreboard.getString("time")));
                }
            }
        }
        return leaderBoard;
    }


    private ArrayList<ArcadeScores> readArcadeScoresFromFile(FileHandle fileHandle) throws IOException{
        ArrayList<ArcadeScores> arcadeScores = new ArrayList<ArcadeScores>();
        JsonReader jsonReader = new JsonReader();

        if(fileHandle.exists()) {
            JsonValue jsonValue = jsonReader.parse(fileHandle);

            if (jsonValue != null) {
                for (JsonValue scoreboard : jsonValue.iterator()) {
                    arcadeScores.add(new ArcadeScores(scoreboard.getInt("level"), scoreboard.getString("name"),
                            scoreboard.getInt("score")));
                }
            }
        }
        return arcadeScores;
    }


    public ArrayList<ArcadeScores> findArcadeScoresFromFile(FileHandle fileHandle, int level) throws IOException{
        ArrayList<ArcadeScores> arcadeScores = new ArrayList<ArcadeScores>();
        JsonReader jsonReader = new JsonReader();

        if(fileHandle.exists()) {
            JsonValue jsonValue = jsonReader.parse(fileHandle);

            if (jsonValue != null) {
                for (JsonValue scoreboard : jsonValue.iterator()) {
                    if (scoreboard.getInt("level") == level) {
                        arcadeScores.add(new ArcadeScores(scoreboard.getInt("level"), scoreboard.getString("name"),
                                scoreboard.getInt("score")));
                    }
                }
            }
        }
        return arcadeScores;
    }

    public void writeArcadeScoreToFile(FileHandle fileHandle, int level, String name, int score)
            throws IOException{
        ArrayList<ArcadeScores> arcadeScores;// = new ArrayList<ArcadeScores>();


        arcadeScores = readArcadeScoresFromFile(fileHandle);

        ArrayList<ArcadeScores> newScores = new ArrayList<ArcadeScores>();

        arcadeScores.add(new ArcadeScores(level, name, score));

        if (!arcadeScores.isEmpty()) {
            int counter = arcadeScores.size() - 1;
            for (int i = counter; i >= 0; i--) {
                if (arcadeScores.get(i).getLevel() == level) {
                    newScores.add(arcadeScores.get(i));
                    arcadeScores.remove(i);
                }
            }

            Collections.sort(newScores, Collections.<ArcadeScores>reverseOrder());

            if (newScores.size() > 3) {
                counter = newScores.size() - 1;
                for (int i = counter; i > 2; i--) {
                    newScores.remove(i);
                }
            }

            for (ArcadeScores aLeaderBoard : newScores) {
                System.out.print(aLeaderBoard.getLevel() + ", ");
                System.out.print(aLeaderBoard.getName() + ", ");
                System.out.print(aLeaderBoard.getScore());
            }
        }

        arcadeScores.addAll(newScores);
        Json json = new Json(JsonWriter.OutputType.json);
        json.addClassTag("Arcade Scores", com.knowledgehut.developments.dodgeem2.SaveData.ArcadeScores.class);
        fileHandle.writeString(json.prettyPrint(arcadeScores), false);
    }

    public void writeJsonToFile(FileHandle fileHandle, String playerName, int playerScore, String playerTime)
            throws IOException{

        ArrayList<HighScores> leaderBoard;
        leaderBoard = readJsonFromFile(fileHandle);
        leaderBoard.add(new HighScores(playerName, playerScore, playerTime));
        Collections.sort(leaderBoard, Collections.<HighScores>reverseOrder());

        //only store the top 10 results
        if (leaderBoard.size() > 10) {
            int counter = leaderBoard.size() - 1;
            for (int i = counter; i > 9; i--) {
                leaderBoard.remove(i);
            }
        }

        for (HighScores aLeaderBoard : leaderBoard) {
            System.out.print(aLeaderBoard.getName() + ", ");
            System.out.print(aLeaderBoard.getScore());
            System.out.print(aLeaderBoard.getTime());
        }

        Json json = new Json(JsonWriter.OutputType.json);
        json.addClassTag("Classic Highscores", com.knowledgehut.developments.dodgeem2.SaveData.HighScores.class);
        fileHandle.writeString(json.prettyPrint(leaderBoard), false);
    }


    public void clearHighScores(FileHandle fileHandle){
        fileHandle.writeString("", false);
    }

    public ArrayList<HighScores> returnSortedJson(FileHandle fileHandle) throws IOException{
        ArrayList<HighScores> leaderBoard;

        leaderBoard = readJsonFromFile(fileHandle);

        if(!leaderBoard.isEmpty()) {
            Collections.sort(leaderBoard);
            Collections.sort(leaderBoard, Collections.<HighScores>reverseOrder());
        }

        return leaderBoard;
    }

    public ArrayList<ArcadeScores> returnSortedArcadeJson(FileHandle fileHandle, int currentLevel) throws IOException{
        ArrayList<ArcadeScores> leaderBoard;
        ArrayList<ArcadeScores> newList = new ArrayList<ArcadeScores>();

        leaderBoard = readArcadeScoresFromFile(fileHandle);

        if (!leaderBoard.isEmpty()) {

            for (ArcadeScores arcadeScores : leaderBoard) {
                if (arcadeScores.getLevel() == currentLevel) {
                    newList.add(arcadeScores);
                }
            }

            if (!newList.isEmpty()) {
                Collections.sort(newList);
                Collections.sort(newList, Collections.<ArcadeScores>reverseOrder());

            }
        }

        return newList;
    }
}
