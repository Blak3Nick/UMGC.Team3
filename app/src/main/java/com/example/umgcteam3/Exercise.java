package com.example.umgcteam3;

import java.io.Serializable;
import java.util.ArrayList;

// Defines what an exercise is in the scope of this program
public class Exercise implements Serializable {
    private ArrayList<Set> allSets;
    public int exerciseNumber;
    public Exercise(int exerciseNumber) {
        this.exerciseNumber = exerciseNumber;
        this.allSets = new ArrayList<>();
    }

    public Exercise(ArrayList<Set> allSets) {
        this.allSets = allSets;
    }

    public ArrayList<Set> getAllSets() {
        return allSets;
    }

    public void setAllSets(ArrayList<Set> allSets) {
        this.allSets = allSets;
    }
    public void addSet(Set set) {
        this.allSets.add(set);
    }
    public int getSize() {
        return allSets.size();
    }

    public Set getSet(int index) {
        return allSets.get(index);
    }
}
