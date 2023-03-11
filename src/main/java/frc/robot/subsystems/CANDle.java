// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;
import com.ctre.phoenix.led.Animation;
import com.ctre.phoenix.led.CANdle;
import com.ctre.phoenix.led.CANdleConfiguration;
import com.ctre.phoenix.led.ColorFlowAnimation;
import com.ctre.phoenix.led.FireAnimation;
import com.ctre.phoenix.led.RainbowAnimation;
import com.ctre.phoenix.led.CANdle.LEDStripType;
import com.ctre.phoenix.led.CANdle.VBatOutputMode; 

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class CANDle extends SubsystemBase {
    CANdle candle = new CANdle(17);

    public void configLEDs() {
        CANdleConfiguration configAll = new CANdleConfiguration();
        configAll.statusLedOffWhenActive = true;
        configAll.disableWhenLOS = true; //will be diff between hold and press
        configAll.stripType = LEDStripType.GRB;
        configAll.brightnessScalar = 0.4;
        configAll.vBatOutputMode = VBatOutputMode.Modulated;
        candle.configAllSettings(configAll, 100);
    }

  public void candleOn(int red, int green, int blue){
    candle.setLEDs(red, green, blue);
  }

  public void rainbowAnimation(double brightness, double speed, int numLeds){
    RainbowAnimation rainbowAnim = new RainbowAnimation(brightness, speed, numLeds);
    candle.animate(rainbowAnim);   
  }

  public void ColorFlowAnimation(int red, int green, int blue){
    ColorFlowAnimation cFlowAnimation = new ColorFlowAnimation(0, 0, 0);
    candle.animate(cFlowAnimation);
  }

  public void fireanimation(int brightness, int speed, int nunLed, int spark, int cooling){
    FireAnimation cFireAnimation = new FireAnimation(brightness, speed, nunLed, spark, cooling);
    candle.animate(cFireAnimation);
  }
}