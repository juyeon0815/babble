<template>
<el-row>
  <el-col :xs="24" :sm="8" :offset="1">
    <div class="profile-box">
      <div class="profile-header">
        <div v-if="state.profile == 'default'" class="circle">
          <img class="profile" :src="require('@/assets/images/default_profile.png')" />
        </div>
        <div v-else class="circle">
          <img :src="state.profile" alt="내 프로필" class="profile"/>
        </div>
        <label for="newProfile"><i class="fas fa-plus"></i></label>
      </div>
      <div class="profile-change-form">
        <input type="file" id="newProfile" name="newProfile" ref="fileInput" @change="handleFileUpload()"><br/>
        <div class="file-search">
          <div class="file-name">
            <p v-if="state.form.file">{{ state.form.file.name }}</p>
          </div>
          <label for="newProfile">찾기</label>
        </div>
        <div class="profile-btn-group">
          <button class="btn-upload" @click="updateProfile">프로필 업로드</button>
          <button class="btn-delete" @click="deleteProfile">프로필 삭제</button>
          <div v-if="state.provider =='google'" class="provider-circle">
            <img class="provider-logo" :src="require('@/assets/images/google-icon.png')" />
          </div>
          <div v-else-if="state.provider == 'kakao'" class="provider-circle">
            <img class="provider-logo" :src="require('@/assets/images/kakao-icon.png')" />
          </div>
          <div v-else class="provider-circle">
            <img class="provider-babble" :src="require('@/assets/images/provider_babble.png')" />
          </div>
        </div>
      </div>
    </div>
  </el-col>

  <el-col :sm="15">
    <div class="password-change-box">
      <p v-if="state.provider == 'babble'" class="password-change-title">비밀번호 변경</p>
      <el-form
        v-if="state.provider == 'babble'"
        :model="state.form"
        status-icon
        :rules="state.rules"
        ref="updateForm"
        :label-position="state.form.align"
        label-width="180px"
        @change="isValid"
        class="formMargin"
      >
        <el-form-item label="기존 비밀번호" prop="password" class="flexChange">
          <el-input
            type="password"
            v-model="state.form.password"
            autocomplete="off"
            class="inputPwd"
          ></el-input>
          <el-button @click="checkPassword" class="checkBtn">확인</el-button
          ><br />
          <el-alert
            v-show="state.isSuccess"
            title="success alert"
            type="success"
            show-icon
            class="alert"
            @close="state.isSuccess = !state.isSuccess"
          ></el-alert>
          <el-alert
            v-show="state.isFail"
            title="failed alert"
            type="error"
            show-icon
            class="alert"
            @close="state.isFail = !state.isFail"
          ></el-alert>
        </el-form-item>
        <el-form-item label="새로운 비밀번호" prop="newPassword" class="flexChange">
          <el-input
            type="password"
            v-model="state.form.newPassword"
            autocomplete="off"
            class="inputNewPwd"
          ></el-input>
        </el-form-item>
        <el-form-item label="새로운 비밀번호 확인" prop="newPasswordConfirm" class="flexChange">
          <el-input
            type="password"
            v-model="state.form.newPasswordConfirm"
            autocomplete="off"
            class="inputNewPwd"
          ></el-input>
        </el-form-item>
        <el-form-item>
          <el-button
            @click="updatePassword"
            :disabled="!state.isVal"
            class="submitBtn"
            >변경</el-button
          >
        </el-form-item>
      </el-form>
    </div>
    <div class="delete-user-box">
      <p class="delete-title">회원 탈퇴</p>
      <p class="delete-content">
        회원 탈퇴 시, Ba:bble이 제공하는 서비스의 이용이 제한됩니다. 정말로
        탈퇴하시겠습니까?
      </p>
      <el-button @click="deleteUser">Adios Ba:bble</el-button>
    </div>
  </el-col>
</el-row>
</template>

<script>
import { reactive, computed, ref } from "vue";
import { useStore } from "vuex";
import { useRouter } from "vue-router";

export default {
  name: "UserInfo",

  setup(props, { emit }) {
    const store = useStore();
    const router = useRouter();
    const updateForm = ref(null);
    const fileInput = ref(null);
    const AWS = require("aws-sdk");

    const state = reactive({
      email: computed(() => {
        return store.getters["auth/getEmail"];
      }),
      profile: computed(() => {
        return store.getters["auth/getProfile"];
      }),
      form: {
        password: "",
        newPassword: "",
        newPasswordConfirm: "",
        align: "left"
      },
      rules: {
        password: [
          { required: true, message: "필수 입력 항목입니다.", trigger: "blur" }
        ],
        newPassword: [
          { required: true, message: "필수 입력 항목입니다.", trigger: "blur" },
          // { min: 9, message: '최소 9글자를 입력해야 합니다.', trigger: 'blur' },
          // { max: 16, message: '최대 16자까지 입력 가능합니다.', trigger: 'blur' },
          // {
          //   trigger: 'blur',
          //   validator (rule, value, callback) {
          //     if (/^(?=.*[A-Za-z])(?=.*\d)(?=.*[$@$!%*#?&])[A-Za-z\d$@$!%*#?&]{6,}$/.test(value)) {
          //       callback()
          //     } else {
          //       callback(new Error('비밀번호는 영문, 숫자, 특수문자가 조합되어야합니다.'))
          //     }
          //   }
          // },
          {
            trigger: "blur",
            validator(rule, value, callback) {
              if (value !== state.form.password) {
                callback();
              } else {
                callback(new Error("새로운 비밀번호를 입력해주세요."));
              }
            }
          }
        ],
        newPasswordConfirm: [
          { required: true, message: "필수 입력 항목입니다.", trigger: "blur" },
          // { min: 9, message: '최소 9글자를 입력해야 합니다.', trigger: 'blur' },
          // { max: 16, message: '최대 16자까지 입력 가능합니다.', trigger: 'blur' },
          // {
          //   trigger: 'blur',
          //   validator (rule, value, callback) {
          //     if (/^(?=.*[A-Za-z])(?=.*\d)(?=.*[$@$!%*#?&])[A-Za-z\d$@$!%*#?&]{6,}$/.test(value)) {
          //       callback()
          //     } else {
          //       callback(new Error('비밀번호는 영문, 숫자, 특수문자가 조합되어야합니다.'))
          //     }
          //   }
          // },
          {
            trigger: "blur",
            validator(rule, value, callback) {
              if (value === state.form.newPassword) {
                callback();
              } else {
                callback(new Error("입력한 비밀번호와 일치하지 않습니다."));
              }
            }
          }
        ]
      },
      isVal: false,
      isSuccess: false,
      isFail: false,
      albumBucketName: "album-bucket-name",
      bucketRegion: "bucket-region",
      IdentityPoolId: "identity-pool-id",
      provider: computed(() => {
        return store.getters["auth/getProvider"];
      }),
    });

    const isValid = function() {
      updateForm.value.validate(valid => {
        if (valid) {
          state.isVal = true;
        } else {
          state.isVal = false;
        }
      });
    };

    const checkPassword = function() {
      store
        .dispatch("auth/requestPasswordCheck", {
          email: state.email,
          password: state.form.password
        })
        .then(function(result) {
          console.log(result.data.message);
          if (result.data.message == "Success") {
            state.isSuccess = true;
          } else if (result.data.message == "fail") {
            state.isFail = true;
          }
        })
        .catch(function(err) {
          console.log(err);
        });
    };

    if (state.provider == 'google' || state.provider == 'kakao') {
      store
        .dispatch("auth/requestSocialUserInfo", {email: state.email})
        .then(function (result) {
          console.log(result, '소셜 로그인 유저 정보 받아오기')
          store.commit("auth/setUserProfile", result.data.picture);
        })
    } else {
      store
        .dispatch("auth/requestUserInfo", localStorage.getItem("jwt"))
        .then(function(result) {
          console.log(result.data.picture);
          store.commit("auth/setUserProfile", result.data.picture);
        })
        .catch(function (err) {
          if (err) {
            console.log(err, '마이페이지에서 유저정보 불러오며 받은 catch')
          }
        })
    }

    const handleFileUpload = function() {
      let test1 = document.getElementsByName("newProfile")[0].files[0];
      let test2 = fileInput.value.files[0];
      console.log(test1, "기존 방식");
      console.log(test2, "ref 방식");
      state.form.file = test2;
      console.log(state.form.file, "파일이 업로드 되었다");
    };

    const updateProfile = function() {
      AWS.config.update({
        region: state.bucketRegion,
        credentials: new AWS.CognitoIdentityCredentials({
          IdentityPoolId: state.IdentityPoolId
        })
      });

      let upload = new AWS.S3.ManagedUpload({
        params: {
          Bucket: state.albumBucketName,
          Key: state.form.file.name,
          Body: state.form.file
        }
      });

      let promise = upload.promise();

      promise.then(
        function(data) {
          swal({
            text: "프로필 이미지를 업로드했습니다.",
            icon: "success",
          });
          store.commit("auth/setUserProfile", data.Location);
          store
            .dispatch("auth/requestUpdateProfile", {
              email: state.email,
              picture: data.Location
            })
            .then(res => console.log(res));
          state.form.file = null
        },
        function(err) {
          return alert(
            "There was an error uploading your photo: ",
            err.message
          );
        }
      );
    };

    const deleteProfile = function() {
      store.commit("auth/setUserProfile", "default");
      const backToDefault = "default";
      store
        .dispatch("auth/requestUpdateProfile", {
          email: state.email,
          picture: backToDefault
        })
        .then(res => console.log(res));
    };

    //비밀번호 변경 후 로그아웃 처리를 해줘야 하나...? 다시 로그인하라고...?
    const updatePassword = function() {
      store
        .dispatch("auth/requestUpdatePassword", {
          email: state.email,
          password: state.form.newPassword
        })
        .then(function(result) {
          console.log(result);
          swal({
            text: "비밀번호가 변경되었습니다.",
            icon: "success",
          });
          state.form.password = "";
          state.form.newPassword = "";
          state.form.newPasswordConfirm = "";
          state.isSuccess = false
          state.isFail = false
          state.isVal = false
        });
    };

    const deleteUser = function() {
      swal({
        text: "정말 Ba:bble에서 탈퇴하시겠습니까?",
        icon: "warning",
        buttons: true,
        dangerMode: true,
      })
      .then((willDelete) => {
        if (willDelete) {
          store
            .dispatch("auth/requestDeleteUser", { email: state.email })
            .then(function(result) {
              swal({
                text: "탈퇴가 완료되었습니다.\n 다시 또 만나요 우리...",
                icon: "info",
              })
              store.commit("auth/setLogout");
              router.push("/")
              .then((() =>window.scrollTo(0,0) ))
            });
        }
      });



    };

    return {
      state,
      updateForm,
      fileInput,
      isValid,
      checkPassword,
      handleFileUpload,
      updateProfile,
      deleteProfile,
      updatePassword,
      deleteUser,
      // clickLogout
    };
  }
};
</script>

<style>
.inputPwd {
  width: 43%;
}

.checkBtn {
  margin-left: 1.5%;
}

.inputNewPwd {
  width: 55%;
}

.submitBtn {
  margin-left: 45%;
}

.alert {
  width: 55%;
}

.profile-box {
  flex-direction: column;
  margin-top: 9%;
}

.profile-header {
  width: 250px;
  height: 250px;
  margin-right: auto;
  margin-left: auto;
  position: relative;
}

.profile-box .circle {
  width: 220px;
  height: 220px;
  border-radius: 70%;
  overflow: hidden;
  margin-top: 10%;
  margin-left: auto;
  margin-right: auto;
  box-shadow: 0px 0px 0px 8px rgba(121, 23, 250, 0.13);
}

.profile-box .circle:hover {
  box-shadow: 0px 0px 0px 12px rgba(113, 28, 250, 0.432);
}

.profile-box .profile {
  width: 100%;
  height: 100%;
  object-fit: cover;
}

.profile-header>label {
  position: absolute;
  display: inline;
  left: 170px;
  bottom: 16px;
  width: 50px;
  height: 50px;
  background-color: rgba(113, 28, 250, 0.432);
  color: white;
  cursor: pointer;
  line-height: 45px;
  border-radius: 70%;
  text-align: center;
}

.profile-change-form input[type="file"] {
  position: absolute;
  width: 1px;
  height: 1px;
  margin: -1px;
  clip: rect(0, 0, 0, 0);
  overflow: hidden;
  padding: 0;
}

.profile-change-form .file-search {
  display: flex;
  justify-content: center;
  align-items: center;
}

.profile-change-form .file-name {
  display: inline-block;
  width: 180px;
  height: 30px;
  border: 1px solid purple;
  border-radius: 5px;
  background: transparent;
}

.profile-change-form .file-search>label {
  display: inline-block;
  width: 70px;
  height: 32px;
  margin-left: 5px;
  background-color:rgba(113, 28, 250, 0.432);
  color: white;
  cursor: pointer;
  line-height: 30px;
  border-radius: 5px;
  text-align: center;
}

.profile-change-form .file-name>p {
  position: relative;
  top: -12px;
  margin-left: 5px;
}

.profile-btn-group {
  margin-top: 4%;
  display: flex;
  justify-content: center;
}

.profile-btn-group .btn-upload {
  width: 100px;
  height: 40px;
  margin-right: 3%;
  border: none;
  border-radius: 10px;
  background-color: rgba(113, 28, 250, 0.432);
  color: white;
  cursor: pointer;
}

.profile-btn-group .btn-delete {
  width: 90px;
  margin-right: 3%;
  border: none;
  border-radius: 10px;
  background-color: rgba(121, 23, 250, 0.13);
  color:  rgba(113, 28, 250, 0.432);
  cursor: pointer;
}

.provider-circle {
  width: 40px;
  height: 40px;
  border-radius: 70%;
  overflow: hidden;
}

.provider-circle .provider-logo {
  width: 100%;
  height: 100%;
  object-fit: cover;
}

.provider-circle .provider-babble {
  width: 110%;
  height:110%;
  object-fit: cover;
}

.password-change-box {
  margin-top: 13%;
  margin-left: 10%;
}

.password-change-box .password-change-title {
  font-size: 1rem;
  font-weight: bold;
}

.el-button:focus, .el-button:hover {
    color: rgba(113, 28, 250, 0.432);
    border-color: rgba(113, 28, 250, 0.432);
    background-color: rgba(121, 23, 250, 0.13) ;
    outline: 0;
}

.delete-user-box {
  margin-top: 16%;
  margin-left: 9%;
}

.delete-user-box .delete-title {
  font-size: 1rem;
  font-weight: bold;
}

.delete-user-box .delete-content {
  font-size: 0.9rem;
  font-weight: bold;
  color: rgb(66, 64, 64)
}

@media screen and (max-width: 480px) {
  .profile-change-form>label {
    left: 18rem;
  }

  .profile-change-form .file-search {
    display: flex;
    flex-direction: row;
    justify-content: center;

  }

  .profile-change-form .file-name {
    margin-left: 0%;
  }

  .el-col-offset-1 {
    margin-left: 0;
  }

  .password-change-box {
    margin-top: 25%;
  }

  .password-change-box .password-change-title {
    margin-left: 8%;
  }

  .flexChange {
    display: flex;
    flex-direction: column;
  }

  .formMargin {
    margin-left: 8%;
  }

  .inputPwd {
    width: 60%;
  }

  .alert {
    width: 80%;
  }

  .inputNewPwd {
    width: 80%;
  }

  .submitBtn {
    margin-left: 33%;
  }

  .delete-user-box {
    margin-left: 17%;
  }

  .delete-user-box .delete-content {
    width: 80%;
  }




}
</style>
