/**
 * @title  Deterministic Finite Automata parser.
 *
 * @course CSC - 652 - Theory of Computation
 *
 * @date   UNCG Fall 2019
 *
 * @author Joshua Crotts
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * This class represents a deterministic finite automaton. DFA's have states,
 * and those states have transition symbols to navigate from one state to
 * another. Each DFA also has a corresponding alphabet to define what symbols
 * are used for transitions.
 *
 * @author Joshua
 */
public class DFA {

    private final List<State> states;
    private final List<State> acceptStates;
    private final char[] alphabet;
    private State startState;

    public DFA (int numberOfStates, char[] alphabet) {
        this.states = new ArrayList<>();
        this.alphabet = alphabet;

        for (int i = 0 ; i < numberOfStates ; i++) {
            this.states.add(new State(i + 1));
        }

        this.acceptStates = new ArrayList<>();
    }

    /**
     * Calls a recursive helper method, and recursively traverses through the
     * string until the string is empty. Returns true if the traversal ended on
     * a final state, false otherwise.
     *
     * Also checks to make sure the string contains only letters from the
     * alphabet.
     *
     * @param s
     * @return
     */
    public boolean parseDFA (String s) {
        return hasValidSymbols(s) && parseDFAHelper(s, this.startState);
    }

    private boolean parseDFAHelper (String s, State state) {
        if (s.length() != 0) {
            char transition = s.charAt(0);
            State transitionState = state.getNextState(transition);
            return this.parseDFAHelper(s.substring(1), transitionState);
        }
        else {
            return this.stateIsFinal(state);
        }
    }

    public void addTransition (State initial) {
        this.states.add(initial);
    }

    public void addFinalState (int finalState) {
        for (State state : states) {
            if (state.getStateName() == finalState) {
                this.addFinalState(state);
                return;
            }
        }
    }

    public void addFinalState (State finalState) {
        this.acceptStates.add(finalState);
    }

    public boolean stateIsFinal (int s) {
        boolean isFinal = false;
        for (State state : this.acceptStates) {
            isFinal = state.getStateName() == s;
        }

        return isFinal;
    }

    public boolean stateIsFinal (State s) {
        return this.acceptStates.contains(s);
    }

    public void setStartState (int startState) {
        for (State state : states) {
            if (state.getStateName() == startState) {
                this.startState = state;
                return;
            }
        }
    }

    public void setStartState (State startState) {
        this.startState = startState;
    }

    public State getState (int n) {
        return this.states.get(n - 1);
    }

    public String getAlphabet () {
        return Arrays.toString(this.alphabet);
    }

    /**
     * Parses the first state in the 3-tuple as an integer. This represents the
     * "from" state.
     *
     * @param inputBuffer 3-tuple representation of from-state, trans, end-state
     * @return
     */
    public static int parseStateName (String inputBuffer) {
        return Integer.parseInt(inputBuffer.substring(0, inputBuffer.indexOf(" ")));
    }

    /**
     * Parses the second value in the 3-tuple as a char. This represents the
     * transition symbol from the "from" state to the "to" state.
     *
     * @param inputBuffer 3-tuple representation of from-state, trans, end-state
     * @return
     */
    public static char parseTransitionSymbol (String inputBuffer) {
        return inputBuffer.charAt(inputBuffer.indexOf(" ") + 1);
    }

    /**
     * Parses the second state in the 3-tuple as an integer. This represents the
     * "to" state.
     *
     * @param inputBuffer 3-tuple representation of from-state, trans, end-state
     * @return
     */
    public static int parseTransitionState (String inputBuffer) {
        return Integer.parseInt(inputBuffer.substring(inputBuffer.lastIndexOf(" ") + 1, inputBuffer.length()));
    }

    /**
     * Iterate through the string. If any of its characters are not present in
     * the alphabet specified by the DFA, we return false.
     *
     * @param s
     * @return
     */
    public boolean hasValidSymbols (String s) {
        boolean hasValidChars;

        for (int i = 0 ; i < s.length() ; i++) {
            hasValidChars = false;
            char c = s.charAt(i);

            for (int j = 0 ; j < this.alphabet.length ; j++) {
                if (s.charAt(i) == this.alphabet[j]) {
                    //
                    //  If we find a matching character, we know we're good
                    //  for this char in the string.
                    //
                    hasValidChars = true;
                    break;
                }
            }

            //  If we don't find a match, return false early.
            if (!hasValidChars) {
                return false;
            }
        }

        return true;
    }

}
