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


    private ArrayList<HighScores> readJsonFromFile(FileHandle fileHandle) throws IOException{
        ArrayList<HighScores> leaderBoard = new ArrayList<HighScores>();
        JsonReader jsonReader = new JsonReader();
        JsonValue jsonValue = jsonReader.parse(fileHandle);

        for(JsonValue scoreboard: jsonValue.iterator()){
            leaderBoard.add(new HighScores(scoreboard.getString("name"), scoreboard.getInt("score")));
        }

        return leaderBoard;
    }

    public void writeJsonToFile(FileHandle fileHandle, String playerName, int playerScore) throws IOException{
        ArrayList<HighScores> leaderBoard = readJsonFromFile(fileHandle);
        leaderBoard.add(new HighScores(playerName, playerScore));
        Collections.sort(leaderBoard, Collections.<HighScores>reverseOrder());

        Json json = new Json(JsonWriter.OutputType.json);
        json.addClassTag("Classic Highscores", com.knowledgehut.developments.dodgeem2.SaveData.HighScores.class);
        fileHandle.writeString(json.prettyPrint(leaderBoard), false);
    }


    public ArrayList<HighScores> returnSortedJson(FileHandle fileHandle) throws IOException{
        ArrayList<HighScores> leaderBoard = readJsonFromFile(fileHandle);

        Collections.sort(leaderBoard);

        Collections.sort(leaderBoard, Collections.<HighScores>reverseOrder());
        return leaderBoard;
    }

}
