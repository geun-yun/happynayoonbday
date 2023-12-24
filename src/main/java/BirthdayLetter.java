package src.main.java;
public class BirthdayLetter {
    public static BirthdayLetter instance = new BirthdayLetter();
    private BirthdayLetter() {}

    public static BirthdayLetter getInstance() {
        return instance;
    }

    public String getLetter() {

        return "사랑하는 나윤이에게,\n\n" +
                "자기 생일 너무 축하하고 낳아주신 부모님한테도 너무 감사하네.\n" +
                "오늘 정말 자기와 함께 있고 싶었는데 같이 못 있게 되어서 너무 미안해.\n" +
                "자기와 함께 시간을 보낸 모든 순간이 너무 뜻깊고 좋았어.\n" +
                "그래도 오늘 자기가 살아있는 것에 대한 감사함을 생일로서 한 번 더 기억하듯이\n" +
                "우리 서로에게 그리고 주변에 좋은 모든 것들의 대한\n" +
                "익숙함이 당연함이 되지 않도록 항상 감사하자!\n" +
                "시간이 빨리 가는 듯 느리게 가는 듯 어쨌든\n" +
                "우리가 벌써 서로를 사랑한지 130일이나 되었네.\n" +
                "자기의 대략 9000일 인생에서는 아직 작은 숫자이지만\n" +
                "앞으로도 130이라는 숫자가 셀 수 없이 커질때가지\n" +
                "계속 서로 아껴주고 도와주고 사랑해주면서\n" +
                "우리 같이 멋진 인생을 만들어 가자!\n" +
                "다시 한번 생일 축하하고 너무 사랑해!\n\n" +
                "영원히 자기편인 근이가";
    }
}
