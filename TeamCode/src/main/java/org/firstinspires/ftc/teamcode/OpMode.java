package org.firstinspires.ftc.teamcode;

import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.command.CommandScheduler;
import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.Commands.drivetrain.TeleopDriveCommand;
import org.firstinspires.ftc.teamcode.Commands.extender.ExtenderSetPosition;
import org.firstinspires.ftc.teamcode.SubSystems.AntiTurret;
import org.firstinspires.ftc.teamcode.SubSystems.Cartridge;
import org.firstinspires.ftc.teamcode.SubSystems.DriveTrain;
import org.firstinspires.ftc.teamcode.SubSystems.Elbow;
import org.firstinspires.ftc.teamcode.SubSystems.Elevator;
import org.firstinspires.ftc.teamcode.SubSystems.Extender;
import org.firstinspires.ftc.teamcode.SubSystems.Intake;
import org.firstinspires.ftc.teamcode.SubSystems.Turret;
import org.firstinspires.ftc.teamcode.Vision.AllianceColor;
import org.firstinspires.ftc.teamcode.Vision.TeamPropDetector;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;

@TeleOp(name = "DriveTrain")
public class OpMode extends CommandOpMode {

    DriveTrain driveTrain;
    Elbow elbow;
    Turret turret;
    AntiTurret antiTurret;
    Cartridge cartridge;
    Elevator elevator;
    BNO055IMU imu;
    TeamPropDetector teamPropDetector;
    OpenCvCamera webcam;
    GamepadEx gamepadEx1;
    GamepadEx gamepadEx2;
    Extender extender;
    Intake intake;

    private double extenderSensitivity;
    private double elbowSensitivity;
    private double antiTurretSensitivity;
    private double cartridgeSensitivity;
    private double lastExtenderPos = 0;
    private double lastElbowPos = 0;
    private double lastAntiTurretPos = 0;
    private double lastCartridgePos = 0;

    @Override
    public void initialize() {
        CommandScheduler.getInstance().reset();

//        initDriveTrain();
//        initIntake();
//        initElevator();
//        initElbow();
//        initTurret();
//        initExtender();
//        initAntiTurret();
        initGamepad();

//        new ArmGetToPosition(elevator, elbow, extender, turret, antiTurret, ArmPosition.INTAKE, true).withTimeout(1).schedule(); // timeout so it doesn't go up for some reason
    }

    public void initGamepad() {
        gamepadEx1 = new GamepadEx(gamepad1);
        gamepadEx2 = new GamepadEx(gamepad2);
        initCartridge();

//        gamepadEx1.getGamepadButton(GamepadKeys.Button.A).whenPressed(new IntakeRotateToggle(intake.roller));

//        gamepadEx1.getGamepadButton(GamepadKeys.Button.A).whenPressed(new ExtenderSetPosition(extender, Extender.Position.CLOSED));
//        gamepadEx1.getGamepadButton(GamepadKeys.Button.B).whenPressed(new ExtenderSetPosition(extender, Extender.Position.MID_WAY));
//        gamepadEx1.getGamepadButton(GamepadKeys.Button.Y).whenPressed(new ExtenderSetPosition(extender, Extender.Position.OPEN));
//        gamepadEx1.getGamepadButton(GamepadKeys.Button.DPAD_UP).whenPressed(new InstantCommand(this::setElbowSensitivityUp));
//        gamepadEx1.getGamepadButton(GamepadKeys.Button.DPAD_DOWN).whenPressed(new InstantCommand(this::setElbowSensitivityDown));
//        gamepadEx1.getGamepadButton(GamepadKeys.Button.DPAD_RIGHT).whenPressed(new InstantCommand(this::setAntiTurretSensitivityUp));
//        gamepadEx1.getGamepadButton(GamepadKeys.Button.DPAD_LEFT).whenPressed(new InstantCommand(this::setAntiTurretSensitivityDown));
//        gamepadEx1.getGamepadButton(GamepadKeys.Button.DPAD_UP).whenPressed(new InstantCommand(this::setExtenderSensitivityUp));
//        gamepadEx1.getGamepadButton(GamepadKeys.Button.DPAD_DOWN).whenPressed(new InstantCommand(this::setExtenderSensitivityDown));
        gamepadEx1.getGamepadButton(GamepadKeys.Button.DPAD_UP).whenPressed(new InstantCommand(this::setCartridgeSensitivityUp));
        gamepadEx1.getGamepadButton(GamepadKeys.Button.DPAD_DOWN).whenPressed(new InstantCommand(this::setCartridgeSensitivityDown));



//        gamepadEx2.getGamepadButton(GamepadKeys.Button.A).whenPressed(new ArmGetToPosition(elevator, elbow, extender, turret, antiTurret, ArmPosition.SAFE_PLACE, true));
//        gamepadEx2.getGamepadButton(GamepadKeys.Button.B).whenPressed(new ArmGetToPosition(elevator, elbow, extender, turret, antiTurret, ArmPosition.THIRD_TEST_POSITION, true));
//        gamepadEx2.getGamepadButton(GamepadKeys.Button.X).whenPressed(new ArmGetToPosition(elevator, elbow, extender, turret, antiTurret, ArmPosition.INTAKE, true));
//        gamepadEx2.getGamepadButton(GamepadKeys.Button.Y).whenPressed(new ArmGetToPosition(elevator, elbow, extender, turret, antiTurret, ArmPosition.SECOND_TEST_POSITION, true));

//        gamepadEx1.getGamepadButton(GamepadKeys.Button.DPAD_UP).whenPressed(new InstantCommand(ArmPositionSelector::moveUp));
//        gamepadEx1.getGamepadButton(GamepadKeys.Button.DPAD_RIGHT).whenPressed(new InstantCommand(ArmPositionSelector::moveRight));
//        gamepadEx1.getGamepadButton(GamepadKeys.Button.DPAD_DOWN).whenPressed(new InstantCommand(ArmPositionSelector::moveDown));
//        gamepadEx1.getGamepadButton(GamepadKeys.Button.DPAD_LEFT).whenPressed(new InstantCommand(ArmPositionSelector::moveLeft));
//        gamepadEx1.getGamepadButton(GamepadKeys.Button.X).whenPressed(new SetRobotSide(elevator, elbow, extender, turret, antiTurret, Side.LEFT));
//        gamepadEx1.getGamepadButton(GamepadKeys.Button.Y).whenPressed(new SetRobotSide(elevator, elbow, extender, turret, antiTurret, Side.CENTER));
//        gamepadEx1.getGamepadButton(GamepadKeys.Button.B).whenPressed(new SetRobotSide(elevator, elbow, extender, turret, antiTurret, Side.RIGHT));
//        gamepadEx1.getGamepadButton(GamepadKeys.Button.A).whenPressed(new ArmGetToSelectedPosition(elevator, elbow, extender, turret, antiTurret));
//        gamepadEx1.getGamepadButton(GamepadKeys.Button.A).whenPressed(new ElevatorGetToHeightPID(elevator, 30));
//        gamepadEx1.getGamepadButton(GamepadKeys.Button.B).whenPressed(new ElevatorGetToHeightPID(elevator, 15));
//        gamepadEx1.getGamepadButton(GamepadKeys.Button.X).whenPressed(new ElevatorGetToHeightPID(elevator, 0));
//        gamepadEx1.getGamepadButton(GamepadKeys.Button.A).whenPressed(new InstantCommand(() -> elevator.setPower(1)));
//        gamepadEx1.getGamepadButton(GamepadKeys.Button.B).whenPressed(new InstantCommand(() -> elevator.setPower(0)));
//        gamepadEx1.getGamepadButton(GamepadKeys.Button.X).whenPressed(new InstantCommand(() -> elevator.setPower(-1)));



    }


    public void initDriveTrain() {
        initIMU();
        driveTrain = new DriveTrain(hardwareMap, imu);
        driveTrain.setDefaultCommand(new TeleopDriveCommand(driveTrain, gamepad1));
    }

    public void initIntake() {
        intake = new Intake(hardwareMap);
    }

    public void initTurret() {
        turret = new Turret(hardwareMap);
    }

    public void initAntiTurret() {
        antiTurret = new AntiTurret(hardwareMap);
    }

    public void VisionInit() {
        teamPropDetector = new TeamPropDetector(AllianceColor.BLUE);
        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        webcam = OpenCvCameraFactory.getInstance().createWebcam(hardwareMap.get(WebcamName.class, "Weiss cam"), cameraMonitorViewId);

        webcam.openCameraDeviceAsync(new OpenCvCamera.AsyncCameraOpenListener() {
            @Override
            public void onOpened() {
                webcam.startStreaming(640, 480, OpenCvCameraRotation.UPRIGHT);
            }

            @Override
            public void onError(int errorCode) {
            }
        });

        webcam.setPipeline(teamPropDetector);
    }

    public void initElevator() {
        elevator = new Elevator(hardwareMap);
    }

    public void initElbow() {
        elbow = new Elbow(hardwareMap);
    }

    public void initExtender() {
        extender = new Extender(hardwareMap);
    }

    public void initCartridge() {
        cartridge = new Cartridge(hardwareMap, gamepadEx1);
    }

    public void initIMU() {
        imu = hardwareMap.get(BNO055IMU.class, "imu");
        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
        parameters.angleUnit = BNO055IMU.AngleUnit.DEGREES;
        imu.initialize(parameters);
    }

    public void setElbowSensitivityUp() {
        if(elbowSensitivity >= 1) elbowSensitivity = 1;
        else {
            lastElbowPos = elbow.getServoPosition();
            elbowSensitivity += 0.1;
        }
    }

    public void setElbowSensitivityDown() {
        if(elbowSensitivity <= 0) elbowSensitivity = 0;
        else {
            lastElbowPos = elbow.getServoPosition();
            elbowSensitivity -= 0.1;
        }
    }

    public void setAntiTurretSensitivityUp() {
        if(antiTurretSensitivity >= 1) antiTurretSensitivity = 1;
        else {
            lastAntiTurretPos = antiTurret.getPosition();
            antiTurretSensitivity += 0.1;
        }
    }

    public void setAntiTurretSensitivityDown() {
        if(antiTurretSensitivity <= 0) antiTurretSensitivity = 0;
        else {
            lastAntiTurretPos = antiTurret.getPosition();
            antiTurretSensitivity -= 0.1;
        }
    }

    public void setExtenderSensitivityUp() {
        if(extenderSensitivity >= 1) extenderSensitivity = 1;
        else {
            lastExtenderPos = extender.getPos();
            extenderSensitivity += 0.1;
        }
    }

    public void setExtenderSensitivityDown() {
        if(extenderSensitivity <= 0) extenderSensitivity = 0;
        else {
            lastExtenderPos = extender.getPos();
            extenderSensitivity -= 0.1;
        }
    }

    public void setCartridgeSensitivityUp() {
        if(cartridgeSensitivity >= 1) cartridgeSensitivity = 1;
        else {
            lastCartridgePos = cartridge.getPosition();
            cartridgeSensitivity += 0.1;
        }
    }

    public void setCartridgeSensitivityDown() {
        if(cartridgeSensitivity <= 0) cartridgeSensitivity = 0;
        else {
            lastCartridgePos = cartridge.getPosition();
            cartridgeSensitivity -= 0.1;
        }
    }


    @Override
    public void run() {
        super.run();

//        telemetry.addData("Elbow's Sensitivity", elbowSensitivity);
//        telemetry.addData("Anti Turret's Sensitivity", antiTurretSensitivity);
//        telemetry.addData("Extender's Sensitivity", extenderSensitivity);
        telemetry.addData("Cartridge's Sensitivity", cartridgeSensitivity);

//        elbow.setPosition(gamepad1.left_stick_x * 0.2 + 0.2);
//        elbow.setPosition(lastElbowPos + gamepad1.left_stick_x * elbowSensitivity);
//        extender.setPos(lastExtenderPos + gamepad1.left_stick_x * extenderSensitivity);
        cartridge.setPosition(lastCartridgePos + gamepad1.left_stick_x * cartridgeSensitivity);

        telemetry.addData("cartridge calculated position", lastCartridgePos + gamepad1.left_stick_x * cartridgeSensitivity);
//        telemetry.addData("extender calculated position", lastExtenderPos + gamepad1.left_stick_x * extenderSensitivity);
//        telemetry.addData("elbow position", gamepad1.left_stick_x * 0.2 + 0.2);
//        extender.setPos(gamepad1.left_stick_x);
//        elbow.setPosition(1 - gamepad1.left_stick_x);
//        antiTurret.setPos(lastAntiTurretPos + gamepad1.right_stick_x * antiTurretSensitivity);
//        cartridge.setPosition(gamepad1.left_stick_x);
//        telemetry.addData("antiTurret position", lastAntiTurretPos + gamepad1.right_stick_x * antiTurretSensitivity);

//        telemetry.addData("cartridge position", cartridge.getPosition());
//        telemetry.addData("antiTurret pos", antiTurret.getPosition());

//        telemetry.addData("cartridge pos", cartridge.getState());
        telemetry.addData("cartridge commanded position", cartridge.getPosition());
//        telemetry.addData("extender commanded position", extender.getPos());
//        telemetry.addData("elbow pos", elbow.getEncoderPosition());
//        telemetry.addData("elevator height", elevator.getHeight());
//        telemetry.addData("turret angle", turret.getAngle());


//        ArmPositionSelector.telemetry(telemetry);
        telemetry.update();
    }
}
