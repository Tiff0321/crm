<template>
	<el-form ref="formRef" :model="form" :rules="rules" label-width="100px">
		<el-form-item label="ID" prop="id" v-if="edit">
			<el-input v-model="form.id" :disabled="disabledStatus()"></el-input>
		</el-form-item>
		<el-form-item label="名前" prop="name">
			<el-input v-model="form.name" :disabled="false"></el-input>
		</el-form-item>
		<el-form-item label="性別" prop="sex">
			<!-- <el-input v-model.number="form.sex"></el-input> -->
			<el-select v-model="form.sex" placeholder="Select" size="large" style="width: 240px"
    >
	
            <el-option :label="'女'" :value="'female'" selected/>
			<el-option :label="'男'" :value="'male'" />

    </el-select>
		</el-form-item>
		<el-form-item label="電話番号" prop="tel">
			<el-input v-model.trim="form.phone"></el-input>
		</el-form-item>
		
		<!-- <el-form-item label="作成時間" prop="creatDate"> -->
			<!-- <el-input v-model="form.creatDate" :disabled="disabledStatus()"></el-input> 注释了两遍-->
			<!-- <el-date-picker v-model="form.creatDate"  type="datetime" placeholder="Select date and time" :disabled="disabledStatus()" /> -->
		<!-- </el-form-item> -->
		
		<el-form-item label="状態" prop="status">
			<el-switch
				v-model="form.status"
				:active-value="1"
				:inactive-value="0"
				active-text="正常"
				inactive-text="异常"
			></el-switch>
		</el-form-item>
		<!-- <el-form-item label="更新時間" prop="updateDate">
			<el-date-picker type="date" v-model="form.updateDate" value-format="YYYY-MM-DD"></el-date-picker>
		</el-form-item> -->
		<!-- <el-form-item label="アバターをアップロードする" prop="thumb">
			<el-upload
				class="avatar-uploader"
				action="https://run.mocky.io/v3/9d059bf9-4660-45f2-925d-ce80ad6c4d15"
				:show-file-list="false"
				:on-success="handleAvatarSuccess"
				:before-upload="beforeAvatarUpload"
			>
				<img v-if="form.thumb" :src="form.thumb" class="avatar" />
				<el-icon v-else class="avatar-uploader-icon"><Plus /></el-icon>
			</el-upload>
		</el-form-item> -->
		<el-form-item>
			<el-button type="primary" @click="saveEdit(formRef)">保 存</el-button>
		</el-form-item>
	</el-form>
</template>

<script lang="ts" setup>
import { ElMessage, FormInstance, FormRules, UploadProps } from 'element-plus';
import { ref } from 'vue';
import axios from 'axios';//引用Axios
import { useRouter } from 'vue-router';

const router = useRouter(); // 使用 Vue Router 的 useRouter()

// const form.sex = ref('female'); // 设置默认选项为 '女'
const props = defineProps({
	data: {
		type: Object,
		required: true
	},
	edit: {
		type: Boolean,
		required: false
	},
	update: {
		type: Function,
		required: true
	}
});

const defaultData = {
	id: '',
	name: '',
	creatDate: '',
	thumb: '',
	sex: 'female',
	status: 0,
	phone: '',
	updateDate: new Date()
};

const form = ref({ ...(props.edit ? props.data : defaultData) });

//入力状態を判断する
const disabledStatus = () => {
	if(props.edit) {
		return true
	} else {
		return false
	}
};
disabledStatus();

console.log(disabledStatus())


const rules: FormRules = {
	name: [{ required: true, message: '名前', trigger: 'blur' }],
	id: [{ required: true, message: 'IDを入力してください', trigger: 'blur' }]
};
const formRef = ref<FormInstance>();

const saveEdit = async(formEl: FormInstance | undefined) => {
	if (!formEl) return;
	formEl.validate(async(valid) => {
		if (!valid) return false;
		if (!props.edit){
			const response = await axios.post('/api/customers/create', form.value); // POST 请求到后端 API
		console.log('Response:', response.data); // 处理响应
		props.update(form.value);
		ElMessage.success('保存成功！');
		router.push('/table');

		console.log('重定向');

		}
		else if (props.edit){
			const response = await axios.post('/api/customers/update', form.value); // POST 请求到后端 API
		console.log('Response:', response.data); // 处理响应
		props.update(form.value);
		ElMessage.success('修改成功！');
		router.push('/table');

		console.log('重定向');

		}

		

	});
};

const handleAvatarSuccess: UploadProps['onSuccess'] = (response, uploadFile) => {
	form.value.thumb = URL.createObjectURL(uploadFile.raw!);
};

const beforeAvatarUpload: UploadProps['beforeUpload'] = rawFile => {
	if (rawFile.type !== 'image/jpeg') {
		ElMessage.error('Avatar picture must be JPG format!');
		return false;
	} else if (rawFile.size / 1024 / 1024 > 2) {
		ElMessage.error('Avatar picture size can not exceed 2MB!');
		return false;
	}
	return true;
};
</script>

<style>
.avatar-uploader .el-upload {
	border: 1px dashed var(--el-border-color);
	border-radius: 6px;
	cursor: pointer;
	position: relative;
	overflow: hidden;
	transition: var(--el-transition-duration-fast);
}

.avatar-uploader .el-upload:hover {
	border-color: var(--el-color-primary);
}

.el-icon.avatar-uploader-icon {
	font-size: 28px;
	color: #8c939d;
	width: 178px;
	height: 178px;
	text-align: center;
}
</style>
