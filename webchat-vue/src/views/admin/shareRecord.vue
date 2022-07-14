<template>
  <div>
    <div>
      <el-button @click="initShareTableData" icon="el-icon-search" type="primary" size="small" class="topControlsBar">刷新</el-button>
    </div>
    <div style="margin-top: 15px">
      <el-table
              :data="ShareTabledata"
              v-loading="loading"
              border
              stripe
              @selection-change="handleSelectionChange"
              style="width: 100%">
        <el-table-column
            type="selection"
            width="55">
        </el-table-column>
        <el-table-column
            prop="id"
            label="朋友圈消息编号"
            width="80">
        </el-table-column>
        <el-table-column
            prop="username"
            label="发送者昵称"
            width="150">
        </el-table-column>
        <el-table-column
            prop="time"
            label="发送时间"
            width="180">
        </el-table-column>
        <el-table-column
            prop="content"
            label="内容"
            width="450">
        </el-table-column>
        <el-table-column
            prop="picurl"
            lable="picture"
            width="200">
          <template   slot-scope="scope">
            <img :src="scope.row.picurl"  min-width="70" height="70" />
          </template>
        </el-table-column>
        <el-table-column label="操作">
          <template slot-scope="scope">
            <el-button
                size="mini"
                type="danger"
                @click="handleDelete(scope.row)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>
    <div style="display: flex;justify-content: space-between;margin-top: 10px">
      <el-button @click="handleMultiDelete" :disabled="multipleSelection.length==0?true:false" type="danger" icon="el-icon-delete">批量删除</el-button>
    </div>
  </div>
</template>

<script>
export default {
  name: "shareRecord",
  data(){
    return{
      ShareTabledata:[],//数据项
      multipleSelection:[],//选中的每一项
      page:1,//起始页数
      size:10,//单页显示数据数
      total:-1,//表格数据总数
      nameKeyword:'',
      dateScope:null,//时间数组
      loading:false,
    }
  },
  mounted() {
    this.initShareTableData();
  },
  methods:{
    //加载数据
    initShareTableData: function (){
      this.loading =true;
      let url="/moments/";
      this.getRequest(url).then(resp=>{
        this.ShareTabledata = resp;
        console.log(resp);
        this.total = resp.length;
      });
      this.loading=false;
    },
    handleSelectionChange(val){
      this.multipleSelection=val;
    },
    //单个删除
    handleDelete:function (row){
      this.$confirm('此操作将永久删除该条记录, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        //点击确定后即执行
        this.deleteRequest("/moments/"+row.id).then(resp=>{
            this.initShareTableData();
        })
      }).catch(() => {
        //点击了取消
        this.$message({
          type: 'info',
          message: '已取消删除'
        });
      });
    },
    handleMultiDelete:function (){
      this.$confirm('此操作将永久删除该条记录, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        //点击确定后即执行
        this.multipleSelection.forEach(item=>{
          this.deleteRequest("/moments/"+item.id);
        });
        this.initShareTableData();
      }).catch(() => {
        //点击了取消
        this.$message({
          type: 'info',
          message: '已取消删除'
        });
      });
    }
  }
}
</script>

<style scoped>
.nameInput{
  width: 150px;
}
.topControlsBar{
  margin:0 10px;
}
</style>