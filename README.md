# Example-Event-of-SlayTheSpire
This is an example about how to make a simple event in STS.
## 这是一个关于如何在使用你的MOD在游戏中添加事件($events$)的教程
如果你已经了解过如何在游戏中添加卡牌，遗物，人物，但是还尚不清楚如何添加事件，可以参考一下本教程。

### First Step.
假如我们想要编写一个能像玩家手牌中添加卡牌的事件， <br>
<b>首先</b>，在你的MOD目录下创建一个 $events$ 文件夹用以存储你所写的事件，创建一个 $ExampleEvent$ 类，这个类就是你要实现的事件的主体，
```java
public class ExampleEvent
        extends AbstractImageEvent { //该类继承自抽象类AbstractImageEvent
    
}
```
<b>接下来</b>定义构造方法与一些需要用到的常量，它们的具体含义与作用见如下代码注释：
```java
public class ExampleEvent
        extends AbstractImageEvent { //该类继承自抽象类AbstractImageEvent
    public static final String ID = "ExampleMod:ExampleEvent"; //事件的ID
    private static final EventStrings eventStrings = CardCrawlGame.languagePack.getEventString("ExampleMod:ExampleEvent");  //根据ID获取本地化资源
    public static final String NAME = eventStrings.NAME;  //该事件的名称
    public static final String[] DESCRIPTIONS = eventStrings.DESCRIPTIONS;  //事件中的文本描述可以写在这里面
    public static final String[] OPTIONS = eventStrings.OPTIONS;  //事件中选项的文本可以写在这里面

    private static final String DIALOG_1 = DESCRIPTIONS[0];  
    private static final String DIALOG_2 = DESCRIPTIONS[1];
    private static final String DIALOG_3 = DESCRIPTIONS[2];

    private CurScreen screen = CurScreen.INTRO;  //这个枚举类中的变量主要用于控制事件的进程，见下文

    private static AbstractCard c1,c2,c3,c4;  //定义一下我们要添加的四张卡牌

    private enum CurScreen {
        INTRO, CHOICE, RESULT;
    }

    public ExampleEvent() {
        super(NAME, DIALOG_1, "ExampleModResources/img/events/ExampleEvent.jpg");
        this.imageEventText.setDialogOption(OPTIONS[0]);
        setCard();  //设置这四张卡牌具体是哪四张
    }

    public void setCard(){
        c1=new DemonForm();
        c2=new WraithForm();
        c3=new EchoForm();
        c4=new DevaForm();
    }
}
```

### Second Step.
接下来是对事件主体的编写，
```java
protected void buttonEffect(int buttonPressed) {  //这个方法决定着玩家每次与事件中的按钮交互后具体会产生什么效果
                          //这个方法的参数代表着当玩家按下按钮后返回的从上到下按钮的索引，（从0开始）
        
}
```
```java
protected void buttonEffect(int buttonPressed) {
        switch (this.screen) {  //这里我们可以用switch语句来清楚地规划事件的进行
            case INTRO:  //这里我们要求我们的事件具有两个部分（可以参考下面的实现图），首先是触发事件后对事件的文本描述与一个<b>"[继续]"</b>按钮,
                
                return;
            case CHOICE: //点击按钮后，接下来更新描述，并设置4个按钮以让玩家进行对4张卡牌的选择，
                
                
                
                return;
        }
        openMap();
    }
```
首先是case INTRO部分,
```java
case INTRO:
  this.screen = CurScreen.CHOICE;  //执行完 $INTRO$ 后转到下一进程
  this.imageEventText.updateBodyText(DIALOG_2);  //更新一下主体的文本

  this.imageEventText.clearAllDialogs();

  this.imageEventText.setDialogOption(OPTIONS[1],c1.makeStatEquivalentCopy());
  this.imageEventText.setDialogOption(OPTIONS[2],c2.makeStatEquivalentCopy());
  this.imageEventText.setDialogOption(OPTIONS[3],c3.makeStatEquivalentCopy());
  this.imageEventText.setDialogOption(OPTIONS[4],c4.makeStatEquivalentCopy());
  return;
```
     
