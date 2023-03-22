// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;
import com.ctre.phoenix.led.CANdle;
import com.ctre.phoenix.led.CANdleConfiguration;
import com.ctre.phoenix.led.LarsonAnimation;
import com.ctre.phoenix.led.RainbowAnimation;
import com.ctre.phoenix.led.CANdle.LEDStripType;
import com.ctre.phoenix.led.CANdle.VBatOutputMode;
import com.ctre.phoenix.led.LarsonAnimation.BounceMode;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class CANDle extends SubsystemBase {
    CANdle candle = new CANdle(17);

    public void configLEDs() {
        CANdleConfiguration configAll = new CANdleConfiguration();
        configAll.statusLedOffWhenActive = true;
        configAll.disableWhenLOS = true; //will be diff between hold and press
        configAll.stripType = LEDStripType.GRB;
        configAll.brightnessScalar = 0.5;
        configAll.vBatOutputMode = VBatOutputMode.Modulated;
        candle.configAllSettings(configAll, 100);
    }

  public void candleOn(int red, int green, int blue){
    candle.setLEDs(red, green, blue);
  }
  
  public void candleChunkOn(int red, int green, int blue, int white, int startindex, int count) {
    candle.setLEDs(red, green, blue, white, startindex, count);
  }

  public void rainbowAnimation(double brightness, double speed, int numLeds){
    RainbowAnimation rainbowAnim = new RainbowAnimation(brightness, speed, numLeds);
    candle.animate(rainbowAnim);   
  }

  public void larsonAnimation(int numLeds){
  LarsonAnimation larsonAnimation = new LarsonAnimation(250, 40, 146, 0, 0.6, numLeds, BounceMode.Front, 7);
  candle.animate(larsonAnimation);
  }

}