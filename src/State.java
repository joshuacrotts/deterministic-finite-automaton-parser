/**
 * @title  Deterministic Finite Automata parser.
 *
 * @course CSC - 652 - Theory of Computation
 *
 * @date   UNCG Fall 2019
 *
 * @author Joshua Crotts
 */

import java.util.HashMap;

/**
 * This class represents a state in the automaton. Each state has a respective
 * state name, and a HashMap consisting of a transition value, which maps to the
 * destination. For example, state 1 may map "a" to state 2. Thus, a is the key,
 * and state 2 is the value.
 */
public class State {

    private final int stateName;
    private final HashMap<Character, State> transitions;

    public State (int stateName) {
        this.stateName = stateName;
        this.transitions = new HashMap<>();
    }

    /**
     * Returns the state corresponding to the transition (i.e. for the key, it
     * returns the value, that being the state).
     *
     * @param transition
     * @return
     */
    public State getNextState (char transition) {
        return this.transitions.get(transition);
    }

    public void addTransition (char transition, State toState) {
        this.transitions.put(transition, toState);
    }

    public int getStateName () {
        return stateName;
    }
}
