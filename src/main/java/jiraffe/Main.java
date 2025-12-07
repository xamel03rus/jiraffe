package jiraffe;

import jiraffe.core.basis.Engine;

public class Main {
    public static void main(String[] args) {
        (new Main()).run();
    }

    public void run()
    {
        Engine.getInstance().start();
    }
}