package info.masterfrog.sandbox.hamiltoniancycle.util;

import java.util.HashSet;
import java.util.Set;

public class GraphVertexSequencePermutationGeneratorUtil {
    public Set<String> find(String str, int rootLength, int fullLength) {
        Set<String> perm = new HashSet<String>();
        //Handling error scenarios
        if (str == null) {
            return null;
        } else if (str.length() == 0) {
            perm.add("");
            return perm;
        }
        char initial = str.charAt(0); // first character
        String rem = str.substring(1); // Full string without first character
        Set<String> words = find(rem, rootLength, fullLength);
        for (String strNew : words) {
            if (strNew.equals("00111110")) {
                perm.isEmpty();
            }
            for (int i = 0; i <= strNew.length(); i++) {
                boolean skip = false;
                int position = fullLength - strNew.length() + i;
                for (int j = 0; j < rootLength; j++) {
                    // if iteration j is on a matrix order diagonal position
                    // (i.e. if str was a balanced matrix, this would form a diagonal at {0,0}, {1,1}, {2,2}, ...,
                    //  or in flat str notation with root length 4, positions 0, 5, 10, 15)
                    // AND
                    // if the value at that position is a 1
                    // then we can skip this representation, because it doesn't fit the requirement of a zero valued order diagonal matrix
                    // -- and yes, i just made up terms to describe these things :-)
                    if (position == j * rootLength + j) {
                        if (strNew.charAt(i) == '1') {
                            skip = true;
                        }
                    }
                }

                if (skip) {
                    break;
                }

                perm.add(charInsert(strNew, initial, i));
            }
        }
        return perm;
    }

    private String charInsert(String str, char c, int j) {
        String begin = str.substring(0, j);
        String end = str.substring(j);
        return begin + c + end;
    }
}
