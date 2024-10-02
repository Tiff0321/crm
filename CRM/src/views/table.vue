<template>
	<div>
		<div class="container">
			<div class="search-box">
				<el-input v-model="query.name" placeholder="名前/ID" class="search-input mr10" clearable></el-input>
				<el-button type="primary" :icon="Search" @click="handleSearch">検索</el-button>
				<el-button type="warning" :icon="CirclePlusFilled" @click="visible = true">新規</el-button>
			</div>
			<el-table :data="tableData" border class="table" ref="multipleTable" header-cell-class-name="table-header">
				<el-table-column prop="id" label="ID" width="55" align="center"></el-table-column>
				<el-table-column prop="name" label="名前" align="center"></el-table-column>
				<el-table-column label="性別" align="center">
					<template #default="scope">{{ scope.row.sex }}</template>
				</el-table-column>
				<el-table-column prop="phone" label="電話番号" align="center"></el-table-column>
				<el-table-column label="状態" align="center">
					<template #default="scope">
						<el-tag :type="scope.row.status ? 'success' : 'danger'">
							{{ scope.row.status ? '正常' : '異常' }}
						</el-tag>
					</template>
				</el-table-column>

				<el-table-column prop="createdAt" label="作成時間" align="center"></el-table-column>
				<!-- <el-table-column prop="updatedAt" label="更新時間" align="center"></el-table-column> -->
				<el-table-column label="操作" width="280" align="center">
					<template #default="scope">
						<el-button type="warning" size="small" :icon="View" @click="handleView(scope.row)">
							参照
						</el-button>
						<el-button type="primary" size="small" :icon="Edit" @click="handleEdit(scope.$index, scope.row)"
							v-permiss="15">
							修正
						</el-button>
						<!-- <el-button type="danger" size="small" :icon="Delete" @click="handleDelete(scope.$index)" v-permiss="16"> -->
						<el-button type="danger" size="small" :icon="Delete" @click="handleDelete(scope.$index)" >
							削除
						</el-button>
					</template>
				</el-table-column>
			</el-table>
			<div class="pagination">
				<el-pagination background layout="total, prev, pager, next" :current-page="query.pageIndex"
					:page-size="query.pageSize" :total="pageTotal" @current-change="handlePageChange"></el-pagination>
			</div>
		</div>
		<el-dialog :title="idEdit ? '情報の修正' : '新規顧客の新規'" v-model="visible" width="500px" destroy-on-close
			:close-on-click-modal="false" @close="closeDialog">
			<TableEdit :data="rowData" :edit="idEdit" :update="updateData" />
		</el-dialog>
		<el-dialog title="顧客情報の詳細" v-model="visible1" width="700px" destroy-on-close>
			<TableDetail :data="rowData" />
		</el-dialog>
	</div>
</template>

<script setup lang="ts" name="basetable">
import { ref, reactive } from 'vue';
import { ElMessage, ElMessageBox } from 'element-plus';
import { Delete, Edit, Search, CirclePlusFilled, View } from '@element-plus/icons-vue';
import axios from 'axios';
// import { fetchData } from '../api/index';
// import TableEdit from '../components/table-edit.vue';
// import TableDetail from '../components/table-detail.vue';




interface TableItem {
	id: number;
	name: string;
	thumb: string;
	gender: string;
	state: string;
	creatDate: string;
	// 1.定义一个展示数据
	updateDate:string;
	tel: string;
}

const query = reactive({
	creatDate: '',
	//2.查询框反应
	updateDate:'',
	name: '',
	pageIndex: 1,
	pageSize: 10
});
const tableData = ref<TableItem[]>([]);
const pageTotal = ref(0);





// テーブルデータの取得
const getData = async () => {
	
		//使用axios发送GET请求获取数据
		const response =await axios.get('/api/customers/list',{
			params: {
				pageIndex: query.pageIndex,
				pageSize: query.pageSize,
				creatDate: query.creatDate,
				//3.传递给后端参数
				updateDate: query.updateDate,
				name: query.name,
				// id:query.name
			},
		});

		// 假设 API 返回的数据结构如下
		const res = response.data.data;

		console.log(res);
	
	
	// 更新表格数据和总页数
	    tableData.value = res.list;
		pageTotal.value = res.total || 0;
};
getData();

// 検索
const handleSearch = () => {
	query.pageIndex = 1;
	getData();
};
// ページネーションナビゲーション
const handlePageChange = (val: number) => {
	query.pageIndex = val;
	getData();
};

// 削除
const handleDelete = (index: number):void => {
	// 削除の二次確認
	ElMessageBox.confirm('削除してもよろしいですか？', 'Tip', {
		type: 'warning',
		confirmButtonText: '確認',
    cancelButtonText: 'キャンセル',
	})
		.then(() => {
	        //发送删除请求到后端
			const itemId:number=tableData.value[index].id;//假设每个数据项都有一个唯一的id	
			fetch(`/api/customers/delete?id=${itemId}`,{method:'POST',})
			.then((response: Response) => {
					if (!response.ok) {
						throw new Error('网络响应不正常');
					}
					return response.json();
				})
			.then(()=>{
				ElMessage.success('削除成功');
			    tableData.value.splice(index, 1);

			})
			.catch((error: Error) => {
					ElMessage.error('削除失敗: ' + error.message);
				});
			
		})
		.catch(() => { });
};

const visible = ref(false);
let idx: number = -1;
const idEdit = ref(false);
const rowData = ref({});
const handleEdit = (index: number, row: TableItem) => {
	idx = index;
	rowData.value = row;
	idEdit.value = true;
	visible.value = true;
};
const updateData = (row: TableItem) => {
	idEdit.value ? (tableData.value[idx] = row) : tableData.value.unshift(row);
	console.log(tableData.value);
	closeDialog();
};

const closeDialog = () => {
	// alert('close');
	visible.value = false;
	idEdit.value = false;
};

const visible1 = ref(false);
const handleView = (row: TableItem) => {

	rowData.value = row;
	visible1.value = true;
};
</script>

<style scoped>
.search-box {
	margin-bottom: 20px;
}

.search-input {
	width: 200px;
}

.mr10 {
	margin-right: 10px;
}

.table-td-thumb {
	display: block;
	margin: auto;
	width: 40px;
	height: 40px;
}
</style>
