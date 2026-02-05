package org.example.events;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.blue.EchoForm;
import com.megacrit.cardcrawl.cards.green.WraithForm;
import com.megacrit.cardcrawl.cards.purple.DevaForm;
import com.megacrit.cardcrawl.cards.red.DemonForm;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractImageEvent;
import com.megacrit.cardcrawl.helpers.CardHelper;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.vfx.cardManip.PurgeCardEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;

public class ExampleEvent
        extends AbstractImageEvent {
    public static final String ID = "ExampleMod:ExampleEvent";
    private static final EventStrings eventStrings = CardCrawlGame.languagePack.getEventString("ExampleMod:ExampleEvent");
    public static final String NAME = eventStrings.NAME;
    public static final String[] DESCRIPTIONS = eventStrings.DESCRIPTIONS;
    public static final String[] OPTIONS = eventStrings.OPTIONS;

    private static final String DIALOG_1 = DESCRIPTIONS[0];
    private static final String DIALOG_2 = DESCRIPTIONS[1];
    private static final String DIALOG_3 = DESCRIPTIONS[2];
    private CurScreen screen = CurScreen.INTRO;

    private static AbstractCard c1,c2,c3,c4;

    private enum CurScreen {
        INTRO, CHOICE, RESULT;
    }

    public ExampleEvent() {
        super(NAME, DIALOG_1, "ExampleModResources/img/events/ExampleEvent.jpg");
        this.imageEventText.setDialogOption(OPTIONS[0]);
        setCard();
    }

    public void setCard(){
        c1=new DemonForm();
        c2=new WraithForm();
        c3=new EchoForm();
        c4=new DevaForm();
    }

    public void onEnterRoom() {

    }

    protected void buttonEffect(int buttonPressed) {
        switch (this.screen) {
            case INTRO:
                this.screen = CurScreen.CHOICE;
                this.imageEventText.updateBodyText(DIALOG_2);

                this.imageEventText.clearAllDialogs();

                this.imageEventText.setDialogOption(OPTIONS[1],c1.makeStatEquivalentCopy());
                this.imageEventText.setDialogOption(OPTIONS[2],c2.makeStatEquivalentCopy());
                this.imageEventText.setDialogOption(OPTIONS[3],c3.makeStatEquivalentCopy());
                this.imageEventText.setDialogOption(OPTIONS[4],c4.makeStatEquivalentCopy());
                return;
            case CHOICE:
                AbstractPlayer p=AbstractDungeon.player;
                this.screen = CurScreen.RESULT;
                this.imageEventText.clearAllDialogs();
                switch (buttonPressed) {
                    case 0:
                        AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(
                                CardLibrary.getCopy(((AbstractCard)c1).cardID), (Settings.WIDTH / 2),
                                (Settings.HEIGHT / 2)));
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
                }
                this.imageEventText.updateBodyText(DIALOG_3);
                this.imageEventText.setDialogOption(OPTIONS[5]);
                return;
        }
        openMap();
    }
}