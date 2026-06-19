package mutsa.w5homework.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import mutsa.w5homework.domain.Address;

public class AddressDto {
    @Getter
    @NoArgsConstructor
    public static class CreateRequest{
        @NotNull
        private Long memberId;
        @NotBlank
        private String addressName;
        @NotBlank
        private String zipCode;
        @NotBlank
        private String cityAddress;
        @NotBlank
        private String phoneNumber;
    }

    @Getter
    @NoArgsConstructor
    //지금 코드에서는 에러 없지만 예외상황 발생할 경우를 대비해 기본 생성자 어노테이션을 붙여둠.
    public static class UpdateRequest{
        @NotNull
        private String addressName;
        @NotNull
        private String zipCode;
        @NotNull
        private String cityAddress;
        @NotNull
        private String phoneNumber;
    }

    @Getter
    public static class Response{
        private Long id;
        private String addressName;
        private String zipCode;
        private String cityAddress;
        private String phoneNumber;

        public Response(Address address){
            this.id = address.getId();
            this.addressName = address.getAddressName();
            this.zipCode = address.getZipCode();
            this.cityAddress = address.getCityAddress();
            this.phoneNumber = address.getPhoneNumber();
        }
    }
}
