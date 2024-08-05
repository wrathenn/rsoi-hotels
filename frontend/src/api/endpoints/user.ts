import { apiClient } from "api/endpoints/base.ts";
import { UserSchema } from "api/schemas/userSchema.ts";

export namespace UserApi {
    export const getUserInfo = async () => {
        return (await apiClient.get("/me")).data as UserSchema.Dto
    }
}