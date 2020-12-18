package com.example.hypnochi;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class RemainingTimeViewModel extends ViewModel {

    private MutableLiveData<String> currentRemainingTime;

    public MutableLiveData<String> getCurrentRemainingTime() {
        if (currentRemainingTime == null) {
            currentRemainingTime = new MutableLiveData<String>();
        }
        return currentRemainingTime;
    }

    public void setCurrentRemainingTime(MutableLiveData<String> currentRemainingTime) {
        this.currentRemainingTime = currentRemainingTime;
    }
}
