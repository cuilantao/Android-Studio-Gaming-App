package fall2018.csc2017.game2048;

/**
 * This piece of code is taken from
 * https://evgenii.com/blog/spring-button-animation-on-android/
 * It is used to create the bouncing animation when we click on the button!
 */
class MyBounceInterpolator implements android.view.animation.Interpolator {
    private double mAmplitude;
    private double mFrequency;

    MyBounceInterpolator(double amplitude, double frequency) {
        mAmplitude = amplitude;
        mFrequency = frequency;
    }

    public float getInterpolation(float time) {
        return (float) (-1 * Math.pow(Math.E, -time / mAmplitude) *
                Math.cos(mFrequency * time) + 1);
    }
}
