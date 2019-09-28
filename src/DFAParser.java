/**
 * @title  Deterministic Finite Automata parser.
 *
 * @course CSC - 652 - Theory of Computation
 *
 * @date   UNCG Fall 2019
 *
 * @author Joshua Crotts
 */

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class DFAParser {

    private static BufferedReader reader;

    public static void main (String[] args) throws IOException {

        DFA dfa = DFAParser.initializeDFA("dfa.txt");

        //
        //  Test cases for DFA.
        //
        System.out.println(dfa.parseDFA("10")); //1->2->3, 3 is not final so false
        System.out.println(dfa.parseDFA("10010"));//1->2->3->2->2->3 // false
        System.out.println(dfa.parseDFA("001100"));//1-1-2-2-3-2//true

    }

    /**
     * Creates the DFA object by loading in the file and parsing through its
     * information.
     *
     * @param txtFile
     * @return
     * @throws FileNotFoundException
     * @throws IOException
     */
    public static DFA initializeDFA (String txtFile) throws FileNotFoundException, IOException {
        DFAParser.reader = new BufferedReader(new FileReader(txtFile));
        String inputBuffer = "";

        //  Read in the number of states.
        int numberOfStates = Utilities.intParseColon(DFAParser.reader.readLine());

        //  Read in alphabet
        char[] alphabet = Utilities.splitAlphabetStr(DFAParser.reader.readLine());

        //  Instantiate the DFA with the number of states, which creates
        //  n state objects, and the alphabet.
        DFA dfa = new DFA(numberOfStates, alphabet);

        //  Transition begin
        inputBuffer = DFAParser.reader.readLine();

        //  Loop until we hit the end of the transition function
        while (!((inputBuffer = reader.readLine()).contains("Transitions end"))) {

            //
            //  Parse the input from the .txt file.
            //
            int stateName = DFA.parseStateName(inputBuffer);
            char transitionSymbol = DFA.parseTransitionSymbol(inputBuffer);
            int transitionState = DFA.parseTransitionState(inputBuffer);

            State state = dfa.getState(stateName);
            state.addTransition(transitionSymbol, dfa.getState(transitionState));
            dfa.addTransition(state);
        }

        //
        //  Read in the start state specified by the file, and store it.
        //
        int startState = Utilities.intParseColon(DFAParser.reader.readLine());

        //
        //  Read in the final states, split from the string after the colon,
        //  then split them into an integer array.
        //
        inputBuffer = DFAParser.reader.readLine();
        inputBuffer = Utilities.strParseColon(inputBuffer);
        int[] finalStates = Utilities.splitIntegerStr(inputBuffer, " ");

        dfa.setStartState(startState);

        //
        //  Iterates over the final states array, and defines them as final
        //  in the DFA object.
        //
        for (int i = 0 ; i < finalStates.length ; i++) {
            dfa.addFinalState(finalStates[i]);
        }

        return dfa;
    }
}
