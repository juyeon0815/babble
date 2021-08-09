<template>
  <el-container>
    <el-aside width="500px" class="tab">
      <h4>{{ state.email }}님 안녕!</h4>
      <div v-if="state.profile == 'default'">
        <el-avatar :size="90" :src="state.circleUrl"></el-avatar>
        <!-- <div class="block"></div> -->
      </div>
      <div v-else>
        <img :src="state.profile" alt="내 프로필" class="profile"/>
      </div>

      <div>
        <form @submit.prevent>
          <label for="newProfile"><h5>프로필 사진 변경</h5></label>
          <input
            type="file"
            ref="fileInput"
            id="newProfile"
            name="newProfile"
            @change="handleFileUpload()"
          />
          <button @click="updateProfile">+</button><br />
          <div class="el-upload__tip">
            jpg/png files with a size less than 500kb
          </div>
        </form>
        <br />
        <button @click="deleteProfile">프로필 삭제</button>
      </div>
    </el-aside>

    <el-main>
      <el-form
        :model="state.form"
        status-icon
        :rules="state.rules"
        ref="updateForm"
        :label-position="state.form.align"
        label-width="180px"
        @change="isValid"
      >
        <el-form-item label="기존 비밀번호" prop="password">
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
          ></el-alert>
          <el-alert
            v-show="state.isFail"
            title="failed alert"
            type="error"
            show-icon
            class="alert"
          ></el-alert>
        </el-form-item>
        <el-form-item label="새로운 비밀번호" prop="newPassword">
          <el-input
            type="password"
            v-model="state.form.newPassword"
            autocomplete="off"
            class="inputNewPwd"
          ></el-input>
        </el-form-item>
        <el-form-item label="새로운 비밀번호 확인" prop="newPasswordConfirm">
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

      <div>
        <hr />
        <h5>회원 탈퇴</h5>
        <h6>
          회원 탈퇴 시, Ba:bble이 제공하는 서비스의 이용이 제한됩니다. 정말로
          탈퇴하시겠습니까?
        </h6>
        <el-button @click="deleteUser">Adios Ba:bble</el-button>
      </div>
    </el-main>
  </el-container>
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
      circleUrl:
        "https://cube.elemecdn.com/3/7c/3ea6beec64369c2642b92c6726f1epng.png",
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
      albumBucketName: "babble-test-zimin",
      bucketRegion: "ap-northeast-2",
      IdentityPoolId: "ap-northeast-2:bc050f66-b34f-4742-be97-12b75f402f1f"
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

    store
      .dispatch("auth/requestUserInfo", localStorage.getItem("jwt"))
      .then(function(result) {
        console.log(result.data.picture);
        store.commit("auth/setUserProfile", result.data.picture);
      });

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
          alert("Successfully uploaded photo.");
          console.log(data, "자 업로드 된 데이터야");
          store.commit("auth/setUserProfile", data.Location);
          store
            .dispatch("auth/requestUpdateProfile", {
              email: state.email,
              picture: data.Location
            })
            .then(res => console.log(res));
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
          alert("변경되었습니다.");
          state.form.password = "";
          state.form.newPassword = "";
          state.form.newPasswordConfirm = "";
        });
    };

    const deleteUser = function() {
      store
        .dispatch("auth/requestDeleteUser", { email: state.email })
        .then(function(result) {
          console.log("삭제되었니", result);
          alert("다시 또 만나요 우리...See U Soon");
          store.commit("auth/setLogout");
          router.push("/");
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
      deleteUser
    };
  }
};
</script>

<style>
.tab {
  margin-left: 60px;
}
.inputPwd {
  width: 250px;
}
.checkBtn {
  margin-left: 10px;
}
.inputNewPwd {
  width: 330px;
}
.submitBtn {
  margin-left: 260px;
}
.alert {
  width: 330px;
}
.profile {
  width: 200px;
  height: 200px;
  object-fit: cover;
}
</style>
