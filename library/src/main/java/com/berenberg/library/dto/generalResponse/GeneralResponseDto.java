package com.berenberg.library.dto.generalResponse;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;


@Setter
@Getter
@NoArgsConstructor
@Accessors(chain = true)
@ToString
public class GeneralResponseDto<T> {

    private String code;
    private String message;
    private T payload;
}
