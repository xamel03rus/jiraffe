package jiraffe.core.utils.statemachine;

public abstract class State
{
    public String name;

    public abstract void Enter();

    public abstract void Exit();

    public abstract void Stay();
}