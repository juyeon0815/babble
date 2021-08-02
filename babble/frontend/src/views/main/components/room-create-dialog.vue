<template>
  <el-dialog title="방 생성하기" v-model="state.dialogVisible" @close="handleClose">
    <el-form :model="state.form" :rules="state.rules" ref="roomCreateForm" :label-position="state.form.align"  @change="isValid">
      <el-form-item prop="title" label="방 제목" :label-width="state.formLabelWidth" >
        <el-input v-model="state.form.title" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item prop="content" label="방 설명" :label-width="state.formLabelWidth" >
        <el-input v-model="state.form.content" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item prop="category" label="카테고리">
        <el-select v-model="state.form.category" placeholder="카테고리 하나를 골라주세요!">
          <el-option label="sports" value="sports"></el-option>
          <el-option label="cooking" value="cooking"></el-option>
          <el-option label="handcraft" value="handcraft"></el-option>
          <el-option label="music" value="music"></el-option>
          <el-option label="finance" value="finance"></el-option>
          <el-option label="game" value="game"></el-option>
          <el-option label="movie" value="movie"></el-option>
          <el-option label="drawing" value="drawing"></el-option>
          <el-option label="book" value="book"></el-option>
          <el-option label="pet" value="pet"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item prop="hashtag" label="해시태그">
        <div class="inputGroup">
          <el-input placeholder="키워드를 입력해주세요(최대 5개)" ref="saveTagInput" v-model="state.form.inputValue" @blur="handleInputConfirm" :disabled="state.form.count == 5">
            <template #append>
              <el-button icon="el-icon-plus"></el-button>
            </template>
          </el-input>
        </div>
        <el-tag :key="tag" v-for="tag in state.form.roomHashtags" closable :disable-transitions="false" @close="handleCloseTag(tag)">
          {{tag}}
        </el-tag>
      </el-form-item>
      <el-form-item prop="speak" label="참여자 말하기 기본값 설정">
      <el-radio-group v-model="state.form.speak">
        <el-radio label="true">누구나 말하기 허용</el-radio>
        <el-radio label="false">손 들기</el-radio>
      </el-radio-group>
    </el-form-item>
    </el-form>
    <template #footer>
      <span class="dialog-footer">
        <el-button type="primary" @click="clickRoomCreate" :disabled="!state.isVal">방 생성하기</el-button>
      </span>
    </template>
  </el-dialog>
</template>

<script>
import { reactive, computed, ref, watch } from 'vue'
import { useStore } from 'vuex'
import { useRouter } from 'vue-router'

export default {
  name: 'room-create-dialog',

  props: {
    open: {
      type: Boolean,
      default: false
    }
  },

  setup(props, { emit }) {
    const store = useStore()
    const router = useRouter()
    // 마운트 이후 바인딩
    const roomCreateForm = ref(null)
    const saveTagInput = ref(null)

    // Element UI Validators
    // rules의 객체 키 값과 form의 객체 키 값이 같아야 매칭되어 적용됨
    const state = reactive({
      form: {
        title: '',
        content: '',
        category: '',
        speak: true,
        inputValue: '',
        roomHashtags: [],
        count: computed(() => {
        return state.form.roomHashtags.length
      }),
        align: 'left'
      },
      rules: {
        title: [
          { required: true, message: '필수 입력 항목입니다.', trigger: 'blur' },
        ],
        category: [
          { required: true, message: '필수 입력 항목입니다.', trigger: 'change' },
        ],
        hashtag: [
          {
            trigger: 'blur',
            validator (rule, value, callback) {
              if (state.form.roomHashtags.length <= 5) {
                callback()
              } else {
                callback(new Error('최대 5개까지만 입력해주세요'))
              }
            }
          }
        ],
        speak: [
          { required: true, message: '필수 입력 항목입니다.', trigger: 'change' },
        ]
      },
      email: computed(() => {
        return store.getters['root/getEmail']
      }),
      isVal: false,
      dialogVisible: computed(() => props.open),
      formLabelWidth: '120px',
    })

    const isValid = function () {
      roomCreateForm.value.validate((valid) => {
        if (valid) {
          state.isVal = true
        } else {
          state.isVal = false
        }
      })
    }

     const handleCloseTag = function (tag) {
      state.form.roomHashtags.splice(state.form.roomHashtags.indexOf(tag), 1)
    }

    const handleInputConfirm = function () {
      console.log(state.form.inputValue, '들어오니')
      console.log(state.form.roomHashtags, '이게 undefined라고?')
      if (state.form.inputValue == '') {
         console.log('빈 키워드는 입력되지 않고 넘어가기')
          // alert('빈 키워드는 입력되지 않아요!')
        } else {
          state.form.roomHashtags.push(state.form.inputValue)
          state.form.inputValue = ''
      }
    }

    const clickRoomCreate = function () {
      let payload = {
        email: state.email,
        title: state.form.title,
        content: state.form.content,
        thumbnailUrl: null,
        name: state.form.category,
        hashtag: state.form.roomHashtags.join(' '),
        speak: state.form.speak
      }
      console.log(payload)
      // store.dispatch('root/requestRoomCreate', payload)
      // .then(function(result) {
      //   console.log(result)
      // })
    }

    const handleClose = function () {
      state.form.title = ''
      state.form.content = ''
      emit('closeRoomCreateDialog')
    }


    return { roomCreateForm, state, saveTagInput, isValid, handleCloseTag, handleInputConfirm, clickRoomCreate, handleClose }
  }
}

</script>

<style>

</style>
