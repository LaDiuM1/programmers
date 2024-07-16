package org.programmers.level2;

import java.util.*;

public class Solution6ByLv2 {

    /**
     * 매개변수 배열 순회 로직
     *  Map을 사용하여 id와 닉네임 매핑
     *  List<dto>로 들어가고 나간 이력의 id와 Enter, Leave에 해당하는 message를 dto로 매핑 후 List에 추가
     *
     *  List를 순회하여 id에 해당하는 Map의 value와 Enter, Leave에 type에 해당하는 Message를 String 배열로 반환
     */

    public class messageDto {
        String id;
        String message;

        messageDto(String id, String message) { this.id = id; this.message = message; }
    }

    public String[] solution(String[] record) {

        List<messageDto> histories = new ArrayList<>();
        Map<String, String> idAndNicknameMap = new HashMap<>();

        for(String parameter : record) {
            String[] splitParameter = parameter.split(" ");
            String type = splitParameter[0];
            String id = splitParameter[1];

            if(!type.equals("Leave")) {
                String nickname = splitParameter[2];
                idAndNicknameMap.put(id, nickname);
            }

            if (!type.equals("Change")) {
                String message = type.equals("Enter") ? "님이 들어왔습니다." : "님이 나갔습니다.";
                histories.add(new messageDto(id, message));
            }
        }

        return histories.stream().map(messageDto -> idAndNicknameMap.get(messageDto.id) + messageDto.message
        ).toArray(String[]::new);
    }

}