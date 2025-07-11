package com.zzw.api.enums;

public enum QQFaceEnum implements BaseEnum{
    SMILE(0, "Smile"),
    POUT(1, "Pout"),
    BLUSH(2, "Blush"),
    DAZED(3, "Dazed"),
    PROUD(4, "Proud"),
    TEARS(5, "Tears"),
    SHY(6, "Shy"),
    SILENT(7, "Silent"),
    SLEEPY(8, "Sleepy"),
    CRY(9, "Cry"),
    EMBARRASSED(10, "Embarrassed"),
    ANGRY(11, "Angry"),
    PLAYFUL(12, "Playful"),
    GRIN(13, "Grin"),
    SURPRISED(14, "Surprised"),
    SAD(15, "Sad"),
    COOL(16, "Cool"),
    COLD_SWEAT(17, "Cold Sweat"),
    MAD(18, "Mad"),
    VOMIT(19, "Vomit"),
    CHUCKLE(20, "Chuckle"),
    HAPPY(21, "Happy"),
    ROLL_EYES(22, "Roll Eyes"),
    ARROGANT(23, "Arrogant"),
    HUNGRY(24, "Hungry"),
    SLEEPING(25, "Sleeping"),
    SCARED(26, "Scared"),
    SWEAT(27, "Sweat"),
    LAUGH(28, "Laugh"),
    SOLDIER(29, "Soldier"),
    SNEER(30, "Sneer"),
    STRUGGLE(31, "Struggle"),
    CURSE(32, "Curse"),
    QUESTION(33, "Question"),
    SHH(34, "Shh"),
    DIZZY(35, "Dizzy"),
    TORMENTED(36, "Tormented"),
    DEPRESSED(37, "Depressed"),
    SKULL(38, "Skull"),
    BEAT(39, "Beat"),
    GOODBYE(40, "Goodbye"),
    WIPE_SWEAT(41, "Wipe Sweat"),
    HUG(42, "Hug"),
    EVIL_SMILE(43, "Evil Smile"),
    HUMPH_LEFT(44, "Hmph Left"),
    HUMPH_RIGHT(45, "Hmph Right"),
    BOO(46, "Boo"),
    DESPISE(47, "Despise"),
    WRONGED(48, "Wronged"),
    ABOUT_TO_CRY(49, "About to Cry"),
    KISS(50, "Kiss"),
    SCARED_STIFF(51, "Scared Stiff"),
    PITIFUL(52, "Pitiful"),
    KNIFE(53, "Knife"),
    WATERMELON(54, "Watermelon"),
    BEER(55, "Beer"),
    BASKETBALL(56, "Basketball"),
    PINGPONG(57, "Ping Pong"),
    COFFEE(58, "Coffee"),
    MEAL(59, "Meal"),
    PIG(60, "Pig"),
    ROSE(61, "Rose"),
    WITHERED(62, "Withered"),
    LIPS(63, "Lips"),
    HEART(64, "Heart"),
    BROKEN_HEART(65, "Broken Heart"),
    CAKE(66, "Cake"),
    LIGHTNING(67, "Lightning"),
    BOMB(68, "Bomb"),
    DAGGER(69, "Dagger"),
    FOOTBALL(70, "Football"),
    BOTTLE(71, "Bottle"),
    POOP(72, "Poop"),
    MOON(73, "Moon"),
    SUN(74, "Sun"),
    GIFT(75, "Gift"),
    HUGS(76, "Hugs"),
    THUMB_UP(77, "Thumb Up"),
    THUMB_DOWN(78, "Thumb Down"),
    HANDSHAKE(79, "Handshake"),
    VICTORY(80, "Victory"),
    FIST(81, "Fist"),
    SEDUCE(82, "Seduce"),
    PUNCH(83, "Punch"),
    PINKY(84, "Pinky"),
    THUMBS_DOWN(85, "Thumbs Down"),
    THUMBS_UP(86, "Thumbs Up"),
    CHICKEN(87, "Chicken"),
    LOVE(88, "Love"),
    LEAVE(89, "Leave"),
    SIGH(90, "Sigh"),
    HAHA(91, "Haha"),
    HEHE(92, "Hehe"),
    NO(93, "No"),
    FLY_AWAY(94, "Fly Away"),
    DRUMSTICK(95, "Drumstick"),
    PINEAPPLE(96, "Pineapple"),
    HOTDOG(97, "Hotdog"),
    PIZZA(98, "Pizza"),
    ICE_CREAM(99, "Ice Cream");

    private final int code;
    private final String desc;

    QQFaceEnum(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    @Override
    public Integer getCode() {
        return 0;
    }

    @Override
    public String getDesc() {
        return "";
    }

    public static QQFaceEnum fromCode(int code) {
        for (QQFaceEnum face : values()) {
            if (face.code == code) {
                return face;
            }
        }
        return null;
    }

    public static String getDescByCode(int code) {
        QQFaceEnum face = fromCode(code);
        return face != null ? face.getDesc() : "Unknown";
    }
}
