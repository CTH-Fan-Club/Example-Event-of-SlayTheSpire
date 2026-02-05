package org.example.modcore;

import basemod.BaseMod;
import basemod.helpers.RelicType;
import basemod.interfaces.*;
import com.badlogic.gdx.Gdx;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.google.gson.Gson;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.localization.*;
import org.example.cards.*;
import com.badlogic.gdx.graphics.Color;
import org.example.characters.MyCharacter;
import org.example.events.ExampleEvent;
import org.example.relics.MyRelic;

import java.awt.*;
import java.nio.charset.StandardCharsets;

import static com.megacrit.cardcrawl.core.Settings.language;
import static org.example.characters.MyCharacter.PlayerColorEnum.EXAMPLE_GREEN;
import static org.example.characters.MyCharacter.PlayerColorEnum.MY_CHARACTER;

@SpireInitializer
public class ExampleMod implements EditCardsSubscriber, EditStringsSubscriber,
        EditCharactersSubscriber, EditRelicsSubscriber, EditKeywordsSubscriber ,PostInitializeSubscriber{
    // 人物选择界面按钮的图片
    private static final String MY_CHARACTER_BUTTON = "ExampleModResources/img/char/Character_Button.png";
    // 人物选择界面的立绘
    private static final String MY_CHARACTER_PORTRAIT = "ExampleModResources/img/char/Character_Portrait.png";
    // 攻击牌的背景（小尺寸）
    private static final String BG_ATTACK_512 = "ExampleModResources/img/512/bg_attack_512.png";
    // 能力牌的背景（小尺寸）
    private static final String BG_POWER_512 = "ExampleModResources/img/512/bg_power_512.png";
    // 技能牌的背景（小尺寸）
    private static final String BG_SKILL_512 = "ExampleModResources/img/512/bg_skill_512.png";
    // 在卡牌和遗物描述中的能量图标
    private static final String SMALL_ORB = "ExampleModResources/img/char/small_orb.png";
    // 攻击牌的背景（大尺寸）
    private static final String BG_ATTACK_1024 = "ExampleModResources/img/1024/bg_attack.png";
    // 能力牌的背景（大尺寸）
    private static final String BG_POWER_1024 = "ExampleModResources/img/1024/bg_power.png";
    // 技能牌的背景（大尺寸）
    private static final String BG_SKILL_1024 = "ExampleModResources/img/1024/bg_skill.png";
    // 在卡牌预览界面的能量图标
    private static final String BIG_ORB = "ExampleModResources/img/char/card_orb.png";
    // 小尺寸的能量图标（战斗中，牌堆预览）
    private static final String ENEYGY_ORB = "ExampleModResources/img/char/cost_orb.png";
    public static final Color MY_COLOR = new Color(79.0F / 255.0F, 185.0F / 255.0F, 9.0F / 255.0F, 1.0F);

    public ExampleMod() {
        BaseMod.subscribe(this);
        BaseMod.addColor(EXAMPLE_GREEN, MY_COLOR, MY_COLOR, MY_COLOR, MY_COLOR, MY_COLOR, MY_COLOR, MY_COLOR,BG_ATTACK_512,BG_SKILL_512,BG_POWER_512,ENEYGY_ORB,BG_ATTACK_1024,BG_SKILL_1024,BG_POWER_1024,BIG_ORB,SMALL_ORB);
    }

    public static void initialize() {
        new ExampleMod();
    }

    public void receiveEditCharacters() {
        // 向basemod注册人物
        BaseMod.addCharacter(new MyCharacter(CardCrawlGame.playerName), MY_CHARACTER_BUTTON, MY_CHARACTER_PORTRAIT, MY_CHARACTER);
    }

    @Override
    public void receiveEditCards() {
        BaseMod.addCard(new Strike());
        BaseMod.addCard(new CTH_Denfense());
        BaseMod.addCard(new CTH_Staue());
        BaseMod.addCard(new CTH_Bite());
        BaseMod.addCard(new Anger_Attack());
        BaseMod.addCard(new Temp_Boom());
        BaseMod.addCard(new Horr_Think());
        BaseMod.addCard(new Four_Fall());
        BaseMod.addCard(new Temp_82());
        BaseMod.addCard(new Temp_ZZT());
        BaseMod.addCard(new Temp_LYW());
        BaseMod.addCard(new Temp_FLR());
        BaseMod.addCard(new D4C_LT());
        BaseMod.addCard(new SexableDemon());
        BaseMod.addCard(new Sexy_Strike());
        BaseMod.addCard(new LecheryAbsorb());
        BaseMod.addCard(new CTH_Detect());
    }

    @Override
    public void receivePostInitialize(){
        BaseMod.addEvent(ExampleEvent.ID, ExampleEvent.class);
    }

    public void receiveEditStrings() {
        String lang;
        if (language == Settings.GameLanguage.ZHS) {
            lang = "ZHS"; // 如果语言设置为简体中文，则加载ZHS文件夹的资源
        } else {
            lang = "ENG"; // 如果没有相应语言的版本，默认加载英语
        }
        BaseMod.loadCustomStringsFile(CardStrings.class, "ExampleModResources/localization/" + lang + "/cards.json"); // 加载相应语言的卡牌本地化内容。
        // 如果是中文，加载的就是"ExampleResources/localization/ZHS/cards.json"
        BaseMod.loadCustomStringsFile(CharacterStrings.class, "ExampleModResources/localization/" + lang + "/characters.json");
        BaseMod.loadCustomStringsFile(RelicStrings.class, "ExampleModResources/localization/" + lang + "/relics.json");
        BaseMod.loadCustomStringsFile(PowerStrings.class, "ExampleModResources/localization/" + lang + "/powers.json");
        BaseMod.loadCustomStringsFile(UIStrings.class,"ExampleModResources/localization/" + lang + "/ui.json");
        BaseMod.loadCustomStringsFile(EventStrings.class,"ExampleModResources/localization/" + lang + "/events.json");
    }

    public void receiveEditRelics() {
        BaseMod.addRelic(new MyRelic(), RelicType.SHARED); // RelicType表示是所有角色都能拿到的遗物，还是一个角色的独有遗物
    }

    @Override
    public void receiveEditKeywords() {
        Gson gson = new Gson();
        String lang = "ENG";
        if (language == Settings.GameLanguage.ZHS) {
            lang = "ZHS";
        }

        String json = Gdx.files.internal("ExampleModResources/localization/" + lang + "/keywords.json")
                .readString(String.valueOf(StandardCharsets.UTF_8));
        Keyword[] keywords = gson.fromJson(json, Keyword[].class);
        if (keywords != null) {
            for (Keyword keyword : keywords) {
                // 这个id要全小写
                BaseMod.addKeyword("examplemod", keyword.NAMES[0], keyword.NAMES, keyword.DESCRIPTION);
            }
        }
    }

}
