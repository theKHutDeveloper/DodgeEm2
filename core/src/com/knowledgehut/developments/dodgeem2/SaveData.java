package com.knowledgehut.developments.dodgeem2;


import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.JsonWriter;

import java.io.IOException;
import java.util.*;


public class SaveData {

    public class HighScores implements Comparable<HighScores>{
        String name;
        int score;

        HighScores(String name, int score){
            this.name = name;
            this.score = score;
        }

        public String getName(){
            return name;
        }

        public int getScore(){
            return score;
        }

        @Override
        public int compareTo(HighScores other_item) {
            return this.getScore() - other_item.getScore();
        }
    }

    public class Info{
        int level;
        String text;

        Info(int level, String text){
            this.level = level;
            this.text = text;
        }

        public int getLevel(){ return level;}

        public String getText(){ return text;}
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
        JsonValue jsonValue = jsonReader.parse(fileHandle);

        int my_level = 0;
        ArrayList<String> my_data = new ArrayList<String>();

        for(JsonValue data: jsonValue.iterator()){
            System.out.println(data.getInt("level"));
            if(data.getInt("level") == level){
                my_level = data.getInt("level");
                for(JsonValue text: data.get("info").iterator()){
                    System.out.println(text.getString("text"));
                    my_data.add(text.getString("text"));
                }
                return new GameText(my_level, my_data);
            }
        }
        return null;
    }

    public String retrieveJsonFromFile(FileHandle fileHandle, int level)throws Exception{
        String data = null;

        JsonReader jsonReader = new JsonReader();
        JsonValue jsonValue = jsonReader.parse(fileHandle);

        for(JsonValue levelText: jsonValue.iterator()){
            if(levelText.getInt("level") == level){
                data = levelText.getString("text");
                break;
            }
        }
        return data;
    }


    private ArrayList<HighScores> readJsonFromFile(FileHandle fileHandle) throws IOException{
        ArrayList<HighScores> leaderBoard = new ArrayList<HighScores>();
        JsonReader jsonReader = new JsonReader();
        JsonValue jsonValue = jsonReader.parse(fileHandle);

        if(jsonValue != null){
            for (JsonValue scoreboard : jsonValue.iterator()) {
                leaderBoard.add(new HighScores(scoreboard.getString("name"), scoreboard.getInt("score")));
            }
        }
        return leaderBoard;
    }

    public void writeJsonToFile(FileHandle fileHandle, String playerName, int playerScore) throws IOException{
        ArrayList<HighScores> leaderBoard;

        leaderBoard = readJsonFromFile(fileHandle);

        leaderBoard.add(new HighScores(playerName, playerScore));
        Collections.sort(leaderBoard, Collections.<HighScores>reverseOrder());

        //only store the top 10 results
        if(leaderBoard.size() > 10){
            int counter = leaderBoard.size()-1;
            for(int i = counter; i > 9; i--) {
                leaderBoard.remove(i);
            }
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

}
