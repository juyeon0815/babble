<template>
  <el-dialog
    title="방 생성하기"
    v-model="state.dialogVisible"
    @close="handleClose"
    width="35%"
  >
    <el-form
      @submit.prevent
      :model="state.form"
      :rules="state.rules"
      ref="roomCreateForm"
      :label-position="state.form.align"
      @change="isValid"
    >
      <el-form-item
        prop="title"
        label="방 제목"
        :label-width="state.formLabelWidth"
      >
        <el-input v-model="state.form.title" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item
        prop="content"
        label="방 설명"
        :label-width="state.formLabelWidth"
      >
        <el-input v-model="state.form.content" autocomplete="off"></el-input>
      </el-form-item>

      <el-form-item
        label="방 썸네일 업데이트"
        :label-width="state.formLabelWidth"
        class="thumbnail-change"
      >
        <input
          type="file"
          ref="fileInput"
          id="thumbnailUrl"
          name="thumbnailUrl"
          @change="handleFileUpload()"
        />
        <div class="file-search">
          <div class="file-name">
            <p v-if="state.form.file">{{ state.form.file.name }}</p>
          </div>
          <label for="thumbnailUrl">찾기</label>
        </div>
      </el-form-item>
      <el-form-item
        prop="category"
        label="카테고리"
        :label-width="state.formLabelWidth"
      >
        <select v-model="state.form.category" class="select-box">
          <option value="" disabled selected hidden>카테고리</option>
          <option label="sports" value="sports"></option>
          <option label="cooking" value="cooking"></option>
          <option label="handcraft" value="handcraft"></option>
          <option label="music" value="music"></option>
          <option label="finance" value="finance"></option>
          <option label="game" value="game"></option>
          <option label="movie" value="movie"></option>
          <option label="drawing" value="drawing"></option>
          <option label="book" value="book"></option>
          <option label="pet" value="pet"></option>
        </select>
      </el-form-item>
      <el-form-item
        prop="hashtag"
        label="해시태그"
        :label-width="state.formLabelWidth"
      >
        <div class="inputGroup">
          <el-input
            placeholder="키워드를 입력해주세요(최대 5개)"
            ref="saveTagInput"
            v-model="state.form.inputValue"
            @keydown.enter="handleInputConfirm"
            :disabled="state.form.count == 5"
            maxlength="15"
          >
            <template #append>
              <el-button
                icon="el-icon-plus"
                @click="handleInputConfirm"
              ></el-button>
            </template>
          </el-input>
        </div>
        <el-tag
          :key="tag"
          v-for="tag in state.form.roomHashtags"
          closable
          :disable-transitions="false"
          @close="handleCloseTag(tag)"
          class="room-tag"
        >
          {{ tag }}
        </el-tag>
      </el-form-item>
    </el-form>
    <template #footer>
      <span class="dialog-footer">
        <el-button
          type="primary"
          round
          class="create-btn"
          @click="clickRoomCreate"
          :disabled="!state.isVal"
          >방 생성하기</el-button
        >
      </span>
    </template>
  </el-dialog>
</template>

<script>
import { reactive, computed, ref, watch } from "vue";
import { useStore } from "vuex";
import { useRouter } from "vue-router";

export default {
  name: "room-create-dialog",

  props: {
    open: {
      type: Boolean,
      default: false
    }
  },

  setup(props, { emit }) {
    const store = useStore();
    const router = useRouter();
    // 마운트 이후 바인딩
    const roomCreateForm = ref(null);
    const saveTagInput = ref(null);
    const fileInput = ref(null);
    const AWS = require("aws-sdk");

    // Element UI Validators
    // rules의 객체 키 값과 form의 객체 키 값이 같아야 매칭되어 적용됨
    const state = reactive({
      form: {
        title: "",
        content: "",
        category: "",
        inputValue: "",
        roomHashtags: [],
        count: computed(() => {
          return state.form.roomHashtags.length;
        }),
        align: "left"
      },
      rules: {
        title: [
          { required: true, message: "필수 입력 항목입니다.", trigger: "blur" }
        ],
        category: [
          {
            required: true,
            message: "필수 입력 항목입니다.",
            trigger: "change"
          }
        ]
      },
      email: computed(() => {
        return store.getters["auth/getEmail"];
      }),
      isVal: false,
      dialogVisible: computed(() => props.open),
      formLabelWidth: "30%",
      albumBucketName: "babble-test-zimin",
      bucketRegion: "ap-northeast-2",
      IdentityPoolId: "ap-northeast-2:bc050f66-b34f-4742-be97-12b75f402f1f"
    });

    const isValid = function() {
      roomCreateForm.value.validate(valid => {
        if (valid) {
          state.isVal = true;
        } else {
          state.isVal = false;
        }
      });
    };

    const handleCloseTag = function(tag) {
      state.form.roomHashtags.splice(state.form.roomHashtags.indexOf(tag), 1);
    };

    const handleInputConfirm = function() {
      // console.log(state.form.inputValue)
      if (state.form.inputValue == "") {
        console.log("빈 키워드는 입력되지 않고 넘어가기");
        // alert('빈 키워드는 입력되지 않아요!')
      } else if (state.form.inputValue.includes(" ")) {
        console.log("공백문자 포함되지 않도록");
      } else if (state.form.roomHashtags.includes(state.form.inputValue)) {
        console.log("같은 키워드는 입력되지 않도록");
      } else {
        state.form.roomHashtags.push(state.form.inputValue);
        state.form.inputValue = "";
      }
      console.log(state.form.roomHashtags, "추가된 후 해시태그");
    };

    const handleFileUpload = function() {
      let test1 = document.getElementsByName("thumbnailUrl")[0].files[0];
      let test2 = fileInput.value.files[0];
      // console.log(test1, '기존 방식')
      // console.log(test2, 'ref 방식')
      state.form.file = test2;
      console.log(state.form.file, "파일이 업로드 되었다");
    };

    const clickRoomCreate = function() {
      AWS.config.update({
        region: state.bucketRegion,
        credentials: new AWS.CognitoIdentityCredentials({
          IdentityPoolId: state.IdentityPoolId
        })
      });

      console.log(fileInput.value.files.length, "이거 확인해");
      if (fileInput.value.files.length == 0) {
        let joinHashtag = "";
        if (state.form.roomHashtags.length >= 1) {
          joinHashtag = state.form.roomHashtags.join(" ");
        }
        const payload = {
          email: state.email,
          title: state.form.title,
          content: state.form.content,
          thumbnailUrl: "default",
          category: state.form.category,
          hashtag: joinHashtag
        };
        store.dispatch("root/requestRoomCreate", payload).then(res =>
          router.push({
            name: "conference-detail",
            params: {
              conferenceId: res.data.message
            }
          })
        );
        console.log(joinHashtag, "해시태그조인");
        store.dispatch("root/requestHashtagEmail", joinHashtag).then(res => {
          console.log(res);
        });
        store.commit("root/setIsHost", true);
        handleClose();
      } else {
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
            // alert("Successfully uploaded photo.");
            console.log(data, "저장된 데이터");
            let joinHashtag = "";
            if (state.form.roomHashtags.length >= 1) {
              joinHashtag = state.form.roomHashtags.join(" ");
            }
            console.log(joinHashtag, "해시태그조인");
            const payload = {
              email: state.email,
              title: state.form.title,
              content: state.form.content,
              thumbnailUrl: data.Location,
              category: state.form.category,
              hashtag: joinHashtag
            };
            store.dispatch("root/requestRoomCreate", payload).then(res =>
              router.push({
                name: "conference-detail",
                params: {
                  conferenceId: res.data.message
                }
              })
            );
            store.dispatch("root/requestHashtagEmail", joinHashtag);

            store.commit("root/setIsHost", true);
            handleClose();
          },
          function(err) {
            return alert(
              "There was an error uploading your photo: ",
              err.message
            );
          }
        );
      }
    };

    const handleClose = function() {
      state.form.title = "";
      state.form.content = "";
      state.form.category = "";
      state.form.roomHashtags = [];
      thumbnailUrl.value = null;
      emit("closeRoomCreateDialog");
    };

    return {
      roomCreateForm,
      state,
      saveTagInput,
      fileInput,
      isValid,
      handleCloseTag,
      handleInputConfirm,
      handleFileUpload,
      clickRoomCreate,
      handleClose
    };
  }
};
</script>

<style>
.select-box {
  width: 100px;
  padding: 5px;
  border: 1px solid #999;
  border-radius: 3px;
}
.create-btn {
  background-color: #a8a0ff;
  width: 100%;
}

.thumbnail-change input[type="file"] {
  position: absolute;
  width: 1px;
  height: 1px;
  margin: -1px;
  clip: rect(0, 0, 0, 0);
  overflow: hidden;
  padding: 0;
}

.thumbnail-change .file-search {
  display: flex;
  align-items: center;
}

.thumbnail-change .file-name {
  display: inline-block;
  width: 100%;
  height: 35px;
  border: 1px solid #dcdfe6;
  border-radius: 5px;
  background: transparent;
}

.thumbnail-change .file-search > label {
  display: inline-block;
  width: 70px;
  height: 38px;
  margin-left: 5px;
  background-color: #a8a0ff;
  color: white;
  cursor: pointer;
  line-height: 35px;
  border-radius: 5px;
  text-align: center;
}

.thumbnail-change .file-name > p {
  position: relative;
  top: -15px;
  margin-left: 10px;
}

@media screen and (max-width: 480px) {
  .el-dialog {
    width: 80% !important;
  }
}
</style>
