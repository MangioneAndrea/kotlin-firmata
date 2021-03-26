# kotlin-firmata

> :warning: **Work in progress**, the library is not in a stable form yet!

This project's main focus is to make a multi-platform firmata with kotlin. This would open a new DIY world for
developers who don't really want to dive into C, but also to create full support for some platforms like Android

## How to use

1) Implement your version of `connection.Connection` by setting up your protocol. This could be USB, Wi-Fi, Bluetooth
   whatsoever
2) Create new Firmata object
3) Create the element you want to use from the firmata
4) Have fun, experiment create whatever you want!

#### Setup firmata

```kotlin
// Tell the firmata which connection protocol to use
val firmata = Firmata(connection)
```

#### Example Pwm Led

```kotlin
// Create a new led component on pin 11 (on arduino uno it's a PWM)
val led = firmata.PWMLed(11)
// Set a random value between 0 and 1, where 0 is off, and 1 is the maximum brightness
led.setBrightness(Math.random().toFloat())
```

#### Example Servo

```kotlin
// Create a new servo component on pin 6 (on arduino uno it's a PWM)
val servo = firmata.Servo(6)
// Tell the servo to go into a position between 0 and 1. See kotlin docs for further usage
servo.to(0.7F)
```

#### Example Motor with RadioGroup

```kotlin
// Create a new motor component with pins 2 and 3
val motor = firmata.Motor(2, 3)
// Set on radio group change
(root.findViewById(R.id.radioGroup) as RadioGroup).setOnCheckedChangeListener { radioGroup, _ ->
   // Just a when do! :D
    when (radioGroup.checkedRadioButtonId) {
        R.id.backwards -> motor.turnBackward()
        R.id.forwards -> motor.turnForward()
        R.id.stop -> motor.stop()
    }
}
```

# Support me

<div>
	<a href="https://www.buymeacoffee.com/andreamangione">
		<img alt="Buy Me A Coffee" src="https://www.buymeacoffee.com/assets/img/custom_images/yellow_img.png" style="height: auto !important; width: auto !important;" />
	</a>
</div>

# License

kotlin firmata is [MIT licensed](./LICENSE).