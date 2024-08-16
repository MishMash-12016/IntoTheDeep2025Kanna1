package org.firstinspires.ftc.teamcode.Libraries.MMLib;
import com.arcrobotics.ftclib.command.SubsystemBase;
import com.arcrobotics.ftclib.geometry.Vector2d;
import com.qualcomm.hardware.bosch.BNO055IMU;
import com.roboctopi.cuttlefish.utils.Direction;

import org.apache.commons.math3.linear.MatrixUtils;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.linear.RealVector;
import org.firstinspires.ftc.teamcode.Libraries.CuttlefishFTCBridge.src.devices.CuttleMotor;
import org.firstinspires.ftc.teamcode.MMRobot;
import org.firstinspires.ftc.teamcode.Utils.Configuration;

/**
 * this class is an example of a mecanum drive train.
 */
public class SampleDriveTrain extends SubsystemBase {

    final double[][] transformationMatrix = {
            {1, 1, 1}, //frontLeft
            {-1, 1, 1}, //backLeft
            {-1, 1, -1}, //frontRight
            {1, 1, -1} //backRight
    };

    MMRobot mmRobot = MMRobot.getInstance();

    private final CuttleMotor motorFR;
    private final CuttleMotor motorFL;
    private final CuttleMotor motorBL;
    private final CuttleMotor motorBR;
    private final BNO055IMU imu;
    private double yawOffset = 0;

    public SampleDriveTrain() {
        imu = mmRobot.mmSystems.hardwareMap.get(BNO055IMU.class, Configuration.IMU);
        BNO055IMU.Parameters imuParameters = new BNO055IMU.Parameters();
        imuParameters.angleUnit = BNO055IMU.AngleUnit.DEGREES;
        imu.initialize(imuParameters);

        motorFL = new CuttleMotor(mmRobot.mmSystems.controlHub, Configuration.DRIVE_TRAIN_FRONT_LEFT);
        motorBL = new CuttleMotor(mmRobot.mmSystems.controlHub, Configuration.DRIVE_TRAIN_BACK_LEFT);
        motorFR = new CuttleMotor(mmRobot.mmSystems.controlHub, Configuration.DRIVE_TRAIN_FRONT_RIGHT);
        motorBR = new CuttleMotor(mmRobot.mmSystems.controlHub, Configuration.DRIVE_TRAIN_BACK_RIGHT);

        //TODO: reverse motors as needed
        motorFL.setDirection(Direction.REVERSE);
        motorBL.setDirection(Direction.REVERSE);

    }

    public SampleDriveTrain(double lastAngle){
        this();
        setYaw(lastAngle);
    }

    /**
     * this method translates the joystick values into motor power
     * @param x the value on the x axis
     * @param y the value on the y axis
     * @param yaw the value on the yaw axis
     * @return the power array to feed the motors, by this order:
     * <p>
     * frontLeft
     * <p>
     * backLeft
     * <p>
     * frontRight
     * <p>
     * backRight
     */
    private double[] joystickToPower(double x, double y, double yaw) {

        //v = (x, y, yaw)^t (3x1)
        RealVector joystickVector = MatrixUtils.createRealVector(new double[] {
                x,
                y,
                yaw
        });

        RealMatrix matrixT = MatrixUtils.createRealMatrix(transformationMatrix); //4x3

        //calculation of the power needed by T constants
        RealVector powerVector = matrixT.operate(joystickVector); //p = Tv

        double[] powerArray = powerVector.toArray(); //4x1

        //normalize the array
        for(int i = 0; i < powerArray.length; i++) {
            powerArray[i] = powerArray[i] / Math.max(Math.abs(x) + Math.abs(y) + Math.abs(yaw), 1);
        }

        return powerArray;

    }

    private void setMotorPower(double[] power) {
        motorFL.setPower(power[0]);
        motorBL.setPower(power[1]);
        motorFR.setPower(power[2]);
        motorBR.setPower(power[3]);
    }
    public void drive(double x, double y, double yaw) {
        setMotorPower(joystickToPower(x, y, yaw));
    }

    /**
     * this method turns the normal joystick values, from arcade drive, to field oriented drive.
     * @param x the value on the x axis
     * @param y the value on the y axis
     * @param yaw the value on the yaw axis
     */
    public void fieldOrientedDrive(double x, double y, double yaw) {
        Vector2d joystickDirection = new Vector2d(x, y);
        Vector2d fieldOrientedVector = joystickDirection.rotateBy(-getYawInDegrees());
        drive(fieldOrientedVector.getX(), fieldOrientedVector.getY(), yaw);
    }

    /**
     * this method returns the yaw of the robot, while respecting a certain offset.
     * @return the yaw of the robot
     */
    public double getYawInDegrees() {
        return imu.getAngularOrientation().firstAngle + yawOffset;
    }

    /**
     * set a new yaw to the robot
     * @param newYaw the new yaw
     */
    public void setYaw(double newYaw) {
        yawOffset = newYaw - imu.getAngularOrientation().firstAngle;
    }

    /**
     * reset the yaw of the robot (reset field oriented drive)
     */
    public void resetYaw() {
        setYaw(0);
    }

}