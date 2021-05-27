package bots.bongcloudbot;


import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.asList;

public class IncomeRate {

	// Preflop income rates for 4 or more active opponents
    private static final List<Integer> IR7_2 = asList(-6, -462, -422, -397, -459, -495, -469, -433, -383, -336, -274, -188, -39);
    private static final List<Integer> IR7_3 = asList(-180, 21, -347, -304, -365, -418, -447, -414, -356, -308, -248, -163, -1);
    private static final List<Integer> IR7_4 = asList(-148, -69, 67, -227, -273, -323, -362, -391, -334, -287, -223, -133, 32);
    private static final List<Integer> IR7_5 = asList(-121, -38, 31, 122, -198, -230, -270, -303, -309, -259, -200, -103, 64);
    private static final List<Integer> IR7_6 = asList(-174, -95, -10, 64, 206, -151, -175, -204, -217, -235, -164, -72, 23);
    private static final List<Integer> IR7_7 = asList(-208, -135, -47, 35, 108, 298, -87, -106, -112, -128, -124, -26, 72);
    private static final List<Integer> IR7_8 = asList(-184, -164, -83, 2, 93, 168, 420, -5, 6, -10, -10, 22, 126);
    private static final List<Integer> IR7_9 = asList(-146, -128, -111, -26, 64, 153, 245, 565, 134, 118, 118, 151, 189);
    private static final List<Integer> IR7_10 = asList(-88, -68, -46, -29, 59, 155, 268, 383, 765, 299, 305, 336, 373);
    private static final List<Integer> IR7_J = asList(-38, -15, 1, 30, 51, 147, 256, 377, 536, 996, 380, 420, 462);
    private static final List<Integer> IR7_Q = asList(35, 49, 72, 99, 127, 162, 268, 384, 553, 628, 1279, 529, 574);
    private static final List<Integer> IR7_K = asList(117, 141, 167, 190, 223, 261, 304, 423, 591, 669, 764, 1621, 712);
    private static final List<Integer> IR7_A = asList(269, 304, 333, 363, 313, 365, 416, 475, 644, 720, 815, 934, 2043);
    public static final List<List<Integer>> IR7 = asList(IR7_2, IR7_3, IR7_4, IR7_5, IR7_6,
            IR7_7, IR7_8, IR7_9, IR7_10, IR7_J,
            IR7_Q, IR7_K, IR7_A);

    // Preflop income rates for 2 or 3 active opponent
    private static final List<Integer> IR4_2 = asList(-121, -440, -409, -382, -411, -432, -394, -357, -301, -259, -194, -116, 16);
    private static final List<Integer> IR4_3 = asList(-271, -42, -345, -312, -340, -358, -371, -328, -277, -231, -165, -87, 54);
    private static final List<Integer> IR4_4 = asList(-245, -183, 52, -246, -269, -287, -300, -308, -252, -204, -135, -55, 84);
    private static final List<Integer> IR4_5 = asList(-219, -151, -91, 152, -200, -211, -227, -236, -227, -169, -104, -24, 118);
    private static final List<Integer> IR4_6 = asList(-247, -177, -113, -52, 256, -145, -152, -158, -152, -145, -74, 9, 99);
    private static final List<Integer> IR4_7 = asList(-261, -201, -129, -65, 3, 376, -76, -79, -68, -66, -44, 48, 148);
    private static final List<Integer> IR4_8 = asList(-226, -204, -140, -73, -2, 66, 503, 0, 15, 24, 45, 84, 194);
    private static final List<Integer> IR4_9 = asList(-191, -166, -147, -79, -5, 68, 138, 647, 104, 113, 136, 177, 241);
    private static final List<Integer> IR4_10 = asList(-141, -116, -91, -69, -4, 75, 150, 235, 806, 226, 255, 295, 354);
    private static final List<Integer> IR4_J = asList(-89, -67, -41, -12, 7, 82, 163, 248, 349, 965, 301, 348, 410);
    private static final List<Integer> IR4_Q = asList(-29, -3, 22, 51, 80, 108, 185, 274, 379, 423, 1141, 403, 473);
    private static final List<Integer> IR4_K = asList(47, 76, 101, 128, 161, 199, 230, 318, 425, 473, 529, 1325, 541);
    private static final List<Integer> IR4_A = asList(175, 211, 237, 266, 249, 295, 338, 381, 491, 539, 594, 655, 1554);
    public static final List<List<Integer>> IR4 = asList(IR4_2, IR4_3, IR4_4, IR4_5, IR4_6,
            IR4_7, IR4_8, IR4_9, IR4_10, IR4_J,
            IR4_Q, IR4_K, IR4_A);

    // Preflop income rates for 1 active opponent
    private static final List<Integer> IR2_2 = asList(7, -351, -334, -314, -318, -308, -264, -217, -166, -113, -53, 10, 98);
    private static final List<Integer> IR2_3 = asList(-279, 74, -296, -274, -277, -267, -251, -201, -148, -93, -35, 27, 116);
    private static final List<Integer> IR2_4 = asList(-263, -225, 142, -236, -240, -231, -209, -185, -130, -75, -17, 46, 134);
    private static final List<Integer> IR2_5 = asList(-244, -206, -169, 207, -201, -189, -169, -148, -114, -55, 2, 68, 153);
    private static final List<Integer> IR2_6 = asList(-247, -208, -171, -138, 264, -153, -134, -108, -78, -43, 19, 85, 154);
    private static final List<Integer> IR2_7 = asList(-236, -200, -162, -125, -91, 324, -99, -72, -43, -6, 37, 104, 176);
    private static final List<Integer> IR2_8 = asList(-192, -182, -143, -108, -75, -43, 384, -39, -4, 29, 72, 120, 197);
    private static final List<Integer> IR2_9 = asList(-152, -134, -122, -84, -50, -17, 16, 440, 28, 65, 106, 155, 215);
    private static final List<Integer> IR2_10 = asList(-104, -86, -69, -56, -19, 12, 47, 81, 499, 102, 146, 195, 254);
    private static final List<Integer> IR2_J = asList(-52, -35, -19, 0, 11, 46, 79, 113, 149, 549, 161, 212, 271);
    private static final List<Integer> IR2_Q = asList(2, 21, 34, 55, 72, 86, 121, 153, 188, 204, 598, 228, 289);
    private static final List<Integer> IR2_K = asList(63, 79, 98, 116, 132, 151, 168, 200, 235, 249, 268, 647, 305);
    private static final List<Integer> IR2_A = asList(146, 164, 180, 198, 198, 220, 240, 257, 291, 305, 323, 339, 704);
    public static final List<List<Integer>> IR2 = asList(IR2_2, IR2_3, IR2_4, IR2_5, IR2_6,
            IR2_7, IR2_8, IR2_9, IR2_10, IR2_J,
            IR2_Q, IR2_K, IR2_A);

    // constants used in above method:
    private final static int AHEAD = 0;
    private final static int TIED = 1;
    private final static int BEHIND = 2;
}
