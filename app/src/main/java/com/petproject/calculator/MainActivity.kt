package com.petproject.calculator

import android.annotation.SuppressLint
import android.content.ClipData
import android.content.ClipboardManager
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import kotlin.math.round
import kotlin.math.sqrt


@SuppressLint("StaticFieldLeak")
private lateinit var textField : TextView
@SuppressLint("StaticFieldLeak")
private lateinit var answField : TextView
@SuppressLint("StaticFieldLeak")
private lateinit var debugField : TextView
@SuppressLint("StaticFieldLeak")
private lateinit var btn1: ImageButton
@SuppressLint("StaticFieldLeak")
private lateinit var btn2: ImageButton
@SuppressLint("StaticFieldLeak")
private lateinit var btn3: ImageButton
@SuppressLint("StaticFieldLeak")
private lateinit var btn4:ImageButton
@SuppressLint("StaticFieldLeak")
private lateinit var btn5: ImageButton
@SuppressLint("StaticFieldLeak")
private lateinit var btn6: ImageButton
@SuppressLint("StaticFieldLeak")
private lateinit var btn7: ImageButton
@SuppressLint("StaticFieldLeak")
private lateinit var btn8: ImageButton
@SuppressLint("StaticFieldLeak")
private lateinit var btn9: ImageButton
@SuppressLint("StaticFieldLeak")
private lateinit var btn0: ImageButton
@SuppressLint("StaticFieldLeak")
private lateinit var btncomma: ImageButton
@SuppressLint("StaticFieldLeak")
private lateinit var btnAC: ImageButton
@SuppressLint("StaticFieldLeak")
private lateinit var btnDel: ImageButton
@SuppressLint("StaticFieldLeak")
private lateinit var btnMinus: ImageButton
@SuppressLint("StaticFieldLeak")
private lateinit var btnPlus: ImageButton
@SuppressLint("StaticFieldLeak")
private lateinit var btnEql:ImageButton
@SuppressLint("StaticFieldLeak")
private lateinit var btnDiv: ImageButton
@SuppressLint("StaticFieldLeak")
private lateinit var btnMult: ImageButton
@SuppressLint("StaticFieldLeak")
private lateinit var btnProc: ImageButton
@SuppressLint("StaticFieldLeak")
private lateinit var btnSqrt:ImageButton
private var task: String = ""
private var prTask: String = ""
private var answer: String = ""
private var shownText: String = ""
private var opSwap: Boolean = false
private var opFlag: Boolean = false
private var rightFlag: Boolean = false
private var leftFlag: Boolean = false
private var dbzFlag: Boolean = false
private var commaFlag: Boolean = false
private var ansFlag : Boolean = false
private var buttonText : String = ""
private const val TAG = "MyApp"


class MainActivity : AppCompatActivity(), Animation.AnimationListener {
    private lateinit var  inAnimation: Animation
    private lateinit var  outAnimation: Animation




    override fun onCreate(savedInstancestate: Bundle?) {
        super.onCreate(savedInstancestate)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.getDefaultNightMode())
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()
        inAnimation = AnimationUtils.loadAnimation(this, R.anim.alpha_in)
        outAnimation = AnimationUtils.loadAnimation(this, R.anim.alpha_out)
        outAnimation.setAnimationListener(this)
        setContentView(R.layout.activity_main)

        textField = findViewById(R.id.textView)
        answField = findViewById(R.id.textView2)
        btn1 = findViewById(R.id.btn1)
        btn2 = findViewById(R.id.btn2)
        btn3 = findViewById(R.id.btn3)
        btn4 = findViewById(R.id.btn5)
        btn5 = findViewById(R.id.btn6)
        btn6 = findViewById(R.id.btn7)
        btn7 = findViewById(R.id.btn9)
        btn8 = findViewById(R.id.btn10)
        btn9 = findViewById(R.id.btn11)
        btn0 = findViewById(R.id.btn13)
        btnPlus = findViewById(R.id.btn4)
        btnMinus = findViewById(R.id.btn8)
        btnAC = findViewById(R.id.btn12)
        btnDel = findViewById(R.id.btn15)
        btncomma = findViewById(R.id.btn14)
        btnEql = findViewById(R.id.btn16)
        btnDiv = findViewById(R.id.btn19)
        btnMult = findViewById(R.id.btn20)
        btnSqrt = findViewById(R.id.btn17)
        btnProc = findViewById(R.id.btn18)


        val clipboardManager = getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
        answField.setOnClickListener {
            val text: String = answField.text.toString()
            val clipData = ClipData.newPlainText("text", text)
            clipboardManager.setPrimaryClip(clipData)
            Toast.makeText(applicationContext, " Скопировано ", Toast.LENGTH_SHORT).show()
        }
    }

    private fun recognizeButton (button: View) :String {
        var str = ""
       when (button.id) {
           btn0.id -> str = "0"
           btn1.id -> str = "1"
           btn2.id -> str = "2"
           btn3.id -> str = "3"
           btn4.id -> str = "4"
           btn5.id -> str = "5"
           btn6.id -> str = "6"
           btn7.id -> str = "7"
           btn8.id -> str = "8"
           btn9.id -> str = "9"

           btnPlus.id -> str = "+"
           btnMinus.id -> str = "-"
           btnAC.id -> str = "AC"
           btnDel.id -> str = "DEL"
           btncomma.id -> str = "."
           btnEql.id -> str = "="
           btnDiv.id -> str = "/"
           btnMult.id -> str = "x"
           btnSqrt.id -> str = "√"
           btnProc.id -> str = "%"

       }
        return str
    }


    fun onNumberClick(view: View) {
        val button = view as ImageButton
        button.startAnimation(outAnimation)
        buttonText = recognizeButton(button)
        opSwap = false
        btnMinus.isClickable = true
        Log.i(TAG,"task: $task | answer: $answer |  flags:  $leftFlag, $rightFlag,$opFlag,$ansFlag | Numbers is pressed  ")
        dbzFlag = false
        ansFlag = false
        if (buttonText == ".") { //Проверка и запрет повторного ввода точки в один операнд
            if (!commaFlag) {
                task += "."
                shownText += "."
                textField.text = shownText
                commaFlag = true
                btncomma.isClickable = false
            }
            else {
                textField.text = shownText
            }
        }
        else {
            task += buttonText //Вывод числа на экран
            shownText += buttonText
            textField.text = shownText
            Log.i(TAG,"task: $task | answer: $answer |  flags:  $leftFlag, $rightFlag,$opFlag,$ansFlag |  Number in ")
            }
        leftFlag = true
        if (opFlag) rightFlag = true
        if (leftFlag and rightFlag and opFlag and !dbzFlag) {
           Log.i(TAG,"t: $task | a: $answer |  fl: $leftFlag,$rightFlag,$opFlag,$ansFlag | Answer out  ")
            try { answer = strOperands(task)}
           catch (e: IllegalArgumentException) {
               Log.i(TAG,"task: $task | answer: $answer |  flags:  $leftFlag, $rightFlag,$opFlag,$ansFlag | IllegalArgumentException ")
               lock(true,0.3F)
               Toast.makeText(applicationContext,"Ошибка ввода",Toast.LENGTH_SHORT).apply {setGravity(Gravity.CENTER, 0, 0); show() }
               textField.text = null
           }
                  if (!dbzFlag) answField.text = answer
        }
        Log.i(TAG,"task: $task | answer: $answer |  flags:  $leftFlag, $rightFlag,$opFlag,$ansFlag |  End of Number Fun ")

    }

    fun onOperationClick(view: View) {
        Log.i(TAG,"task: $task | answer: $answer |  flags:  $leftFlag, $rightFlag,$opFlag,$ansFlag |  Operator is pressed ")
        val button = view as ImageButton
        button.startAnimation(outAnimation)
        buttonText = recognizeButton(button)

        if (buttonText == "DEL") {
            Log.i(TAG,"task: $task | answer: $answer |  prT: $prTask | shwn: $shownText| DEL is present")
            opSwap = true
            btnMinus.isClickable = true
            Log.i(TAG,"task: $task | answer: $answer |  prT: $prTask | shwn: $shownText| 1")
            task = removeLast(task)
            Log.i(TAG,"task: $task | answer: $answer |  prT: $prTask | shwn: $shownText| 2")
            shownText = task.replace("\\s".toRegex(), "")
            Log.i(TAG,"task: $task | answer: $answer |  prT: $prTask | shwn: $shownText| 3")
            textField.text = shownText
            Log.i(TAG,"task: $task | answer: $answer |  prT: $prTask | shwn: $shownText| 4")
            commaFlag = false
            btncomma.isClickable = true
            answField.text = null
            if (leftFlag and rightFlag and opFlag and !dbzFlag) {
                try { answer = strOperands(task)}
                catch (e: IllegalArgumentException) {
                    Log.i(TAG,"task: $task | answer: $answer |  flags:  $leftFlag, $rightFlag,$opFlag,$ansFlag | IllegalArgumentException ")
                    lock(true,0.3F)
                    Toast.makeText(applicationContext,"Ошибка ввода",Toast.LENGTH_SHORT).apply {setGravity(Gravity.CENTER, 0, 0); show() }
                    textField.text = null
                }
            if (!dbzFlag) answField.text = answer
            }
            opSwap = false
        } else {
            if (answField.text.isNotEmpty()) {
                when (ansFlag) {
                    false -> { //Знак - оператор, можно считать
                        if (leftFlag and rightFlag and opFlag and !dbzFlag) {
                            try { answer = strOperands(task)}
                            catch (e: IllegalArgumentException) {
                                Log.i(TAG,"task: $task | answer: $answer |  flags:  $leftFlag, $rightFlag,$opFlag,$ansFlag | IllegalArgumentException ")
                                lock(true,0.3F)
                                Toast.makeText(applicationContext,"Ошибка ввода",Toast.LENGTH_SHORT).apply {setGravity(Gravity.CENTER, 0, 0); show() }
                                textField.text = null
                            }
                            if (!dbzFlag) answField.text = answer
                            rightFlag = false
                        }
                        prTask = task
                        task = answer
                        leftFlag = true
                        rightFlag = false
                        opFlag = false
                        ansFlag = true
                        Log.i(TAG,"task: $task | answer: $answer |  flags:  $leftFlag, $rightFlag,$opFlag,$ansFlag |   Answer field isNotEmpty")
                    }

                    true -> { //Знак - знак перед числом, ждем ввод еще одного числа
                        leftFlag = true
                        rightFlag = false
                    }
                }
            }
            if (buttonText == "=") {
                if (answer == "0.0") {
                    clearTask()

                }
                else {
                    textField.text = answField.text
                    shownText = answer
                    task = answer
                    answField.text = null
                    leftFlag = true
                    answer = ""
                }
                    rightFlag = false
                    opFlag = false
                    commaFlag = false
                    opSwap = false
                    btncomma.isClickable = true
                    if (dbzFlag) {
                        lock(true,0.3F)
                        answField.text = "На 0 делить нельзя"
                        textField.text = null
                    }
                Log.i(TAG,"task: $task | answer: $answer |  flags:  $leftFlag, $rightFlag,$opFlag,$ansFlag | Equals is in  ")
                 }
            else {
                    if (buttonText == "AC") {
                        lock(false,1F)
                        clearTask()
                    } else {
                        when (buttonText) {

                            "/" -> {
                                if (opSwap) {
                                    Log.i(TAG,"task: $task | answer: $answer |  flags:  $leftFlag, $rightFlag,$opFlag,$ansFlag | DEL is present")
                                    task = removeLast(task)
                                    shownText = task.replace("\\s".toRegex(), "")
                                    textField.text = shownText
                                    commaFlag = false
                                    btncomma.isClickable = true
                                    answField.text = null
                                    task += opOrSign("/", leftFlag, opFlag, ansFlag)
                                    shownText += "/"
                                    textField.text = shownText
                                    if (leftFlag and rightFlag and opFlag and !dbzFlag) {
                                        try { answer = strOperands(task)}
                                        catch (e: IllegalArgumentException) {
                                            Log.i(TAG,"task: $task | answer: $answer |  flags:  $leftFlag, $rightFlag,$opFlag,$ansFlag | IllegalArgumentException ")
                                            lock(true,0.3F)
                                            Toast.makeText(applicationContext,"Ошибка ввода",Toast.LENGTH_SHORT).apply {setGravity(Gravity.CENTER, 0, 0); show() }
                                            textField.text = null
                                        }
                                        if (!dbzFlag) answField.text = answer
                                    }
                                }
                                else {
                                    task += opOrSign("/", leftFlag, opFlag, ansFlag)
                                    shownText += "/"
                                    textField.text = shownText
                                    opSwap = true
                                }

                            }
                            "x" -> {
                                if (opSwap) {
                                    Log.i(TAG,"task: $task | answer: $answer |  flags:  $leftFlag, $rightFlag,$opFlag,$ansFlag | DEL is present")
                                    task = removeLast(task)
                                    shownText = task.replace("\\s".toRegex(), "")
                                    textField.text = shownText
                                    commaFlag = false
                                    btncomma.isClickable = true
                                    answField.text = null
                                    task += opOrSign("x", leftFlag, opFlag, ansFlag)
                                    shownText += "x"
                                    textField.text = shownText
                                    if (leftFlag and rightFlag and opFlag and !dbzFlag) {
                                        try { answer = strOperands(task)}
                                        catch (e: IllegalArgumentException) {
                                            Log.i(TAG,"task: $task | answer: $answer |  flags:  $leftFlag, $rightFlag,$opFlag,$ansFlag | IllegalArgumentException ")
                                            lock(true,0.3F)
                                            Toast.makeText(applicationContext,"Ошибка ввода",Toast.LENGTH_SHORT).apply {setGravity(Gravity.CENTER, 0, 0); show() }
                                            textField.text = null
                                        }
                                        if (!dbzFlag) answField.text = answer
                                    }
                                }
                                else {
                                    task += opOrSign("x", leftFlag, opFlag, ansFlag)
                                    shownText += "x"
                                    textField.text = shownText
                                    opSwap = true
                                }


                            }
                            "+" -> {
                                if (opSwap) {
                                    Log.i(TAG,"task: $task | answer: $answer |  flags:  $leftFlag, $rightFlag,$opFlag,$ansFlag | DEL is present")
                                    task = removeLast(task)
                                    shownText = task.replace("\\s".toRegex(), "")
                                    textField.text = shownText
                                    commaFlag = false
                                    btncomma.isClickable = true
                                    task += opOrSign("+", leftFlag, opFlag, ansFlag)
                                    shownText += "+"
                                    textField.text = shownText


                                }
                                else {
                                    task += opOrSign("+", leftFlag, opFlag, ansFlag)
                                    shownText += "+"
                                    textField.text = shownText
                                    opSwap = true
                                }

                            }
                            "-" -> {
                                task += opOrSign("-", leftFlag, opFlag, ansFlag)
                                shownText += "-"
                                textField.text = shownText

                            }
                            "√" -> {
                                task += " √ "
                                shownText += "√"
                                textField.text = shownText
                                opFlag = true
                                leftFlag = true
                                opSwap = true



                            }
                            "%" -> {
                                    task += " % "
                                    shownText += "%"
                                    textField.text = shownText
                                    opSwap = true
                                Log.i(TAG,"task: $task | answer: $answer |  flags:  $leftFlag, $rightFlag,$opFlag,$ansFlag | % in  ")
                                if (!ansFlag) { //Считаем процент 50% = 0.5
                                    Log.i(TAG,"task: $task | answer: $answer |  flags:  $leftFlag, $rightFlag,$opFlag,$ansFlag | Naked percent  ")
                                    opFlag = true
                                    rightFlag = true
                                    if (leftFlag and rightFlag and opFlag and !dbzFlag) {
                                        try { answer = strOperands(task)}
                                        catch (e: IllegalArgumentException) {
                                            Log.i(TAG,"task: $task | answer: $answer |  flags:  $leftFlag, $rightFlag,$opFlag,$ansFlag | IllegalArgumentException ")
                                            lock(true,0.3F)
                                            Toast.makeText(applicationContext,"Ошибка ввода",Toast.LENGTH_SHORT).apply {setGravity(Gravity.CENTER, 0, 0); show() }
                                            textField.text = null
                                        }
                                        Log.i(TAG,"task: $task | answer: $answer |  flags:  $leftFlag, $rightFlag,$opFlag,$ansFlag |  Naked percent answer ")
                                        if (!dbzFlag) answField.text = answer
                                    }
                                    opSwap = false
                                }
                                else { //Считаем процент от какого-то числа Х + n%
                                    opFlag = true
                                    rightFlag = true
                                    Log.i(TAG,"task: $task | answer: $answer |  flags:  $leftFlag, $rightFlag,$opFlag,$ansFlag | Percent from N in  ")
                                    if (leftFlag and rightFlag and opFlag and !dbzFlag) {
                                        try { answer = strOperands(task,prTask)}
                                        catch (e: IllegalArgumentException) {
                                            Log.i(TAG,"task: $task | answer: $answer |  flags:  $leftFlag, $rightFlag,$opFlag,$ansFlag | IllegalArgumentException ")
                                            lock(true,0.3F)
                                            Toast.makeText(applicationContext,"Ошибка ввода",Toast.LENGTH_SHORT).apply {setGravity(Gravity.CENTER, 0, 0); show() }
                                            textField.text = null
                                        }
                                        Log.i(TAG,"task: $task | answer: $answer |  flags:  $leftFlag, $rightFlag,$opFlag,$ansFlag | Percent from N answer  ")
                                        if (!dbzFlag) answField.text = answer
                                        task = answer
                                        opFlag = false
                                    }
                                    opSwap = false
                                }

                            }
                        }
                        Log.i(TAG,"task: $task | answer: $answer |  flags:  $leftFlag, $rightFlag,$opFlag,$ansFlag | End of Operator Fun  ")
                    }
                }
            }

    }


  private fun removeLast(str: String) :String{
      Log.i(TAG,"task: $task | answer: $answer |  flags:  $leftFlag, $rightFlag,$opFlag,$ansFlag | remove fun is present ")
      val nstr: String
      val leftOp: String = str.substringBefore(" ")
      val rightOp: String = str.substringAfterLast(" ")
      var oper: String
      if (rightOp.isNotEmpty()) {
        if ((leftOp.toDoubleOrNull() == rightOp.toDoubleOrNull()) and (str.length < (leftOp.length + rightOp.length))) nstr =  leftOp
        else
        {
        oper = str.substringBeforeLast(" ")
        oper = oper.substringAfter(" ")
            when (true){
                (rightOp.toDoubleOrNull() == null) -> rightFlag = false
                ((rightOp.toDouble() < 0) and (rightOp.toDouble() >=  -9)) -> rightFlag = false
                (rightOp.length == 1) -> rightFlag = false
            }
        nstr = "$leftOp $oper $rightOp"
        }
    }
    else
    {
        oper = str.substringAfter(" ")
        oper = oper.substringBefore(" ")
        nstr = leftOp + oper
        opFlag = false
        rightFlag = false
    }
      return nstr.replaceFirst(".$".toRegex(), "")
  }

    private fun strOperands(strTask: String, previousTask : String = "" ): String {
        Log.i(TAG,"task: $task | answer: $answer |  flags:  $leftFlag, $rightFlag,$opFlag,$ansFlag | strOperands fun in  ")
        val leftOp: String = strTask.substringBefore(" ")
        val rightOp: String = strTask.substringAfterLast(" ")
        var oper: String = strTask.substringBeforeLast(" ")
        oper = oper.substringAfter(" ") // обгрызли строку на операнды и оператор
        Log.i(TAG,"task: $task | answer: $answer |  flags:  $leftFlag, $rightFlag,$opFlag,$ansFlag | $leftOp|$rightOp|$oper strOperands fun out  ")

    return solution(leftOp, rightOp, oper, previousTask).toString()
    }

    private fun solution(left: String, right: String, op: String, str : String): Double {
        Log.i(TAG,"task: $task | answer: $answer |  flags:  $leftFlag, $rightFlag,$opFlag,$ansFlag | solution fun in  ")
        var op1 = ""
        var op2: String
        var x: Double = when (left) {
            "" -> 0.0
            else -> left.toDouble()
        }
        var y: Double = when (right) {
            "" -> 0.0
            "." -> 0.0
            "-." -> -0.0
            "+." -> 0.0
            else -> right.toDouble()
        }
        if (str.isNotEmpty()) {
            x = str.substringBefore(" ").toDouble()
            op2 = str.substringAfter(" ")
            op2 = op2.substringBefore(" ")
            y = str.substringAfterLast(" ").toDouble()
            Log.i(TAG,"task: $task | answer: $answer | op2$op2| op1$op1| x $x | y $y || |$str| | str isNotEmpty ")
            return math(op2, x, math("x", x, math("%", y, y))) //Где-то косячит возврат
        }
        when (true)
        {
           (op.contains("√")) ->
            { if (op.length > 1) {
                op2 = op.substringAfterLast(" ") //+
                op1 = op.substringBefore(" ")// sqrt x = 2, y = 4
                return math(op1, x, math(op2, x, y))
            }
            }
        }
        Log.i(TAG,"task: $task | answer: $answer |  f$op|$x|$y | return of solution fun  ")
        return math(op,x,y)
    }

    private fun math(operator : String, x :Double, y : Double): Double {
        var result = 0.0
        when (operator) {
            "%" -> result = x * 0.01
            "√" -> result = sqrt(y)
            "+" -> result = x + y
            "-" -> result = x - y
            "x" -> result = x * y
            "/" -> {
                if (y == 0.0) {
                    dbzFlag = true
                    leftFlag = false
                } else result = x / y
            }
        }
        return result.round(5)
    }
    private fun Double.round(decimals: Int): Double {
        var multiplier = 1.0
            repeat(decimals) { multiplier *= 10 }
        return round(this * multiplier) / multiplier
    }

    private fun clearTask(){
        task = ""
        answer = ""
        shownText = ""
        answField.text = null
        rightFlag = false
        leftFlag = false
        opFlag = false
        commaFlag = false
        ansFlag = false
        btncomma.isClickable = true
        textField.text = task
    }

    private fun lock(state : Boolean, alpha : Float) {
        btn1.isClickable = !state
        btn2.isClickable = !state
        btn3.isClickable = !state
        btn4.isClickable = !state
        btn5.isClickable = !state
        btn6.isClickable = !state
        btn7.isClickable = !state
        btn8.isClickable = !state
        btn9.isClickable = !state
        btn0.isClickable = !state
        btnPlus.isClickable = !state
        btnMinus.isClickable = !state
        btnDel.isClickable = !state
        btncomma.isClickable = !state
        btnEql.isClickable = !state
        btnDiv.isClickable = !state
        btnMult.isClickable = !state
        btnSqrt.isClickable = !state
        btnProc.isClickable = !state

        btn1.alpha = alpha
        btn2.alpha = alpha
        btn3.alpha = alpha
        btn4.alpha = alpha
        btn5.alpha = alpha
        btn6.alpha = alpha
        btn7.alpha = alpha
        btn8.alpha = alpha
        btn9.alpha = alpha
        btn0.alpha = alpha
        btnPlus.alpha = alpha
        btnMinus.alpha = alpha
        btnDel.alpha = alpha
        btncomma.alpha = alpha
        btnEql.alpha = alpha
        btnDiv.alpha = alpha
        btnMult.alpha = alpha
        btnSqrt.alpha = alpha
        btnProc.alpha = alpha

    }
    private fun opOrSign (str: String, fl: Boolean, fo: Boolean, fa: Boolean) : String{
        val newstr: String
        if ((!fl) or ((fl) and (fo)) or ((fl) and (fo) and(fa))) {
           newstr = str // число
            rightFlag = false
           commaFlag = false
           btncomma.isClickable = true
        }
        else {
            newstr = " $str " //оператор
            opFlag = true
            commaFlag = false
            btncomma.isClickable = true
        }
    return newstr
    }

    override fun onAnimationStart(p0: Animation?) {

    }

    override fun onAnimationEnd(p0: Animation?) {

    }

    override fun onAnimationRepeat(p0: Animation?) {

    }
//end


}