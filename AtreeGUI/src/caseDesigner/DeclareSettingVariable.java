package caseDesigner;

public class DeclareSettingVariable {
    static double stepsVerticalGap = 60;
    static double stepsHorizontalGap = 100;
    static int curveRadius = 10;
    static boolean manualStepEntry = false;
	public static double getStepsVerticalGap() {
		return stepsVerticalGap;
	}
	public static void setStepsVerticalGap(double stepsVerticalGap) {
		DeclareSettingVariable.stepsVerticalGap = stepsVerticalGap;
	}
	public static double getStepsHorizontalGap() {
		return stepsHorizontalGap;
	}
	public static void setStepsHorizontalGap(double stepsHorizontalGap) {
		DeclareSettingVariable.stepsHorizontalGap = stepsHorizontalGap;
	}
	public static int getCurveRadius() {
		return curveRadius;
	}
	public static void setCurveRadius(int curveRadius) {
		DeclareSettingVariable.curveRadius = curveRadius;
	}
	public static boolean isManualStepEntry() {
		return manualStepEntry;
	}
	public static void setManualStepEntry(boolean manualStepEntry) {
		DeclareSettingVariable.manualStepEntry = manualStepEntry;
	}
}
