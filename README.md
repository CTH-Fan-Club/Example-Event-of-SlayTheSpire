# Example-Event-of-SlayTheSpire
This is an example about how to make a simple event in STS.
## 这是一个关于如何在使用你的MOD在游戏中添加事件($events$)的教程
如果你已经了解过如何在游戏中添加卡牌，遗物，但是还尚不清楚如何添加事件，可以参考一下本教程。

### First Step.
假如我们想要编写一个能像玩家手牌中添加卡牌的事件（最终实现的效果图可以参考文件里“效果图”文件夹里的图片）， <br>
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
        //第3个参数是你resources文件夹下事件图片的存储路径，分辨率一般为600x600，会显示在事件栏左侧

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

  this.imageEventText.clearAllDialogs();  //清空所有的选项（按钮）
        //添加新的选项
  this.imageEventText.setDialogOption(OPTIONS[1],c1.makeStatEquivalentCopy());
  this.imageEventText.setDialogOption(OPTIONS[2],c2.makeStatEquivalentCopy());
  this.imageEventText.setDialogOption(OPTIONS[3],c3.makeStatEquivalentCopy());
  this.imageEventText.setDialogOption(OPTIONS[4],c4.makeStatEquivalentCopy());
  return;
```
##### imageEventText.setDialogOption(String text)        //为你的事件添加文本为text的选项
##### imageEventText.setDialogOption(String text,AbstractCard card)        //当你的鼠标落在选项上时，可以在选项右侧显示一张卡牌card的缩略图
游戏内部提供的更多方法在此就不展开说了，可以去参考反编译代码。<br>
下面是CHOICE部分，
```java
case CHOICE:
                AbstractPlayer p=AbstractDungeon.player;        //首先获取一下当前楼层中的玩家
                this.screen = CurScreen.RESULT;        //和上文一样，为事件的下一步切换进程
                this.imageEventText.clearAllDialogs();
                switch (buttonPressed) {        //buttonPressed获取的即是玩家点击按钮后的返回值，一般来说，0代表从上往下第一个按钮，1代表第二个......
                    case 0:
                        AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(
                                CardLibrary.getCopy(((AbstractCard)c1).cardID), (Settings.WIDTH / 2),
                                (Settings.HEIGHT / 2)));        //获取一张卡，并播放获得卡牌的特效（动画），这样设置x,y位置大概是相对于屏幕从正中央开始播放
                        break;

                    case 1:
                        AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(
                                CardLibrary.getCopy(((AbstractCard)c2).cardID), (Settings.WIDTH / 2),
                                (Settings.HEIGHT / 2)));
                        break;
                    case 2:
                        AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(
                                CardLibrary.getCopy(((AbstractCard)c3).cardID), (Settings.WIDTH / 2),
                                (Settings.HEIGHT / 2)));
                        break;
                    case 3:
                        AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(
                                CardLibrary.getCopy(((AbstractCard)c4).cardID), (Settings.WIDTH / 2),
                                (Settings.HEIGHT / 2)));
                        break;

                this.imageEventText.updateBodyText(DIALOG_3);        
                this.imageEventText.setDialogOption(OPTIONS[5]);
                return;
```
最后在switch语句结束后，不要忘了写一个`openMap()`来让玩家回到我们的地图中去，这部分最终的代码可以参考我上传的文件，

### Third Step.
最后，别忘了在 $BaseMod$ 中注册一下该事件，并写一下你的本地化文本（如果你加了本地化的话）
```java
    @Override
    public void receivePostInitialize(){
        BaseMod.addEvent(ExampleEvent.ID, ExampleEvent.class);
    }

    public void receiveEditStrings() {
        ......省略
        BaseMod.loadCustomStringsFile(EventStrings.class,"ExampleModResources/localization/" + language + "/events.json");
    }

```
```java
{
  "ExampleMod:ExampleEvent": {
    "NAME": "示例事件",
    "DESCRIPTIONS": [
      "你走进了一个漆黑的房间",
      "在无边的黑暗中，你仿佛看到了远处闪烁着4种颜色的光",
      "你感觉到身体发生了某种变化"
    ],
    "OPTIONS": [
      "[继续]",
      "[走向红光] 获得 #r恶魔形态",
      "[走向绿光] 获得 #g幽魂形态",
      "[走向蓝光] 获得 #b回响形态",
      "[走向紫光] 获得 #p天人形态",
      "[继续前进]"
    ]
  }
}
```
