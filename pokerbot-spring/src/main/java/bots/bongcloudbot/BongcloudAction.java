package bots.bongcloudbot;

public enum BongcloudAction {
    PREMIUM(5),
    RERAISE(4),
    RAISE(3),
    CALL(2),
    LIMP(1),
    FOLD(0),
    INVALID(-1);

    private final int value;

    BongcloudAction(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public BongcloudAction oneUp() {
        switch (this.value) {
            case 0:
                return BongcloudAction.LIMP;
            case 1:
                return BongcloudAction.CALL;
            case 2:
                return BongcloudAction.RAISE;
            case 3:
                return BongcloudAction.RERAISE;
            case 4:
                return BongcloudAction.PREMIUM;
            default:
                return BongcloudAction.INVALID;
        }
    }

    public BongcloudAction oneDown() {
        switch (this.value) {
            case 1:
                return BongcloudAction.FOLD;
            case 2:
                return BongcloudAction.LIMP;
            case 3:
                return BongcloudAction.CALL;
            case 4:
                return BongcloudAction.RAISE;
            case 5:
                return BongcloudAction.RERAISE;
            default:
                return BongcloudAction.INVALID;
        }
    }

    public static BongcloudAction convert(int number) {
        switch (number) {
            case 0:
                return BongcloudAction.FOLD;
            case 1:
                return BongcloudAction.LIMP;
            case 2:
                return BongcloudAction.CALL;
            case 3:
                return BongcloudAction.RAISE;
            case 4:
                return BongcloudAction.RERAISE;
            case 5:
                return BongcloudAction.PREMIUM;
            default:
                return BongcloudAction.INVALID;
        }
    }
}
