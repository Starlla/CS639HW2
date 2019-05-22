package com.pace.cs639spring.hw2;

import java.util.ArrayList;

public class AnimalItem {
    public int mImageResource;
    public ArrayList<String> mFactList;
    public String mFact;
    public String mName;
    public int currentFactIndex;


    public AnimalItem(int imageResource, String name, String string){
        mImageResource = imageResource;
        mFact = string;
        mName = name;
        mFactList = new ArrayList<>();
        mFactList.add(mFact);
        currentFactIndex = 0;
    }

    public int getImageResource(){
        return mImageResource;
    }

    public String getText(){

        return mName + "\n" + mFact;
    }

    public void addFact(String text) {
        mFactList.add(text);
    }

    public void goNextFact() {

        currentFactIndex = currentFactIndex< mFactList.size()-1?currentFactIndex+1:0;
        mFact = mFactList.get(currentFactIndex);
    }

    public boolean deleteFact(){
        if(mFactList.size()>1) {
            mFactList.remove(currentFactIndex);
            currentFactIndex = currentFactIndex - 1;
            return true;
        }
            mFact = mFactList.get(currentFactIndex);
            System.out.println(mFact);
        return false;
    }




    }


