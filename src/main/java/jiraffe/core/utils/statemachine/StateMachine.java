package jiraffe.core.utils.statemachine;

import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;

public class StateMachine
{
    private State currentState;

    private ArrayList<State> states;

    private Map<Integer, Integer> transitions;

    private ArrayList<Object> values;

    public void AddValue(Object value)
    {
        values.add(value);
    }

    public void RemoveValue(Object value)
    {
        values.remove(value);
    }

    public void RemoveValue(int hashCode)
    {
        Object value = values.stream().filter(v -> v.hashCode() == hashCode).findAny().orElse(null);

        if (value == null) {
            return;
        }

        values.remove(value);
    }

    public void AddTransition(int fromHash, int toHash)
    {
        transitions.put(fromHash, toHash);
    }

    public void RemoveTransition(int fromHash, int toHash)
    {
        transitions.remove(fromHash, toHash);
    }

    public void AddState(State state)
    {
        states.add(state);
    }

    public void RemoveState(State state)
    {
        states.remove(state);
    }

    public void RemoveState(int hashCode)
    {
        State state = states.stream().filter(s -> s.hashCode() == hashCode).findAny().orElse(null);

        if (state == null) {
            return;
        }

        states.remove(state);
    }

    public void RemoveState(String name)
    {
        State state = states.stream().filter(s -> Objects.equals(s.name, name)).findAny().orElse(null);

        if (state == null) {
            return;
        }

        states.remove(state);
    }

    public void CheckState()
    {
        
    }
}
