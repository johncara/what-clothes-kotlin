package com.caracode.whatclothes.common;

import com.caracode.whatclothes.common.dagger.ApplicationComponent;

public class ComponentManager {

    private static final ComponentManager INSTANCE = new ComponentManager();
    private ApplicationComponent applicationComponent;

    private ComponentManager() { /* singleton */ }

    public static ComponentManager instance() {
        return INSTANCE;
    }

    public void set(ApplicationComponent applicationComponent) {
        this.applicationComponent = applicationComponent;
    }

    public ApplicationComponent get() {
        return applicationComponent;
    }
}
