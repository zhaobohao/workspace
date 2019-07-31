<template>
  <table v-if='data!=""'>
    <tr>
      <th width='10%' style="word-wrap:break-word;overflow:hidden;word-break:break-all;">名称</th>
      <th width='10%' style="word-wrap:break-word;overflow:hidden;word-break:break-all;">类型</th>
      <th>示例值</th>
      <th width='20%'>描述</th>
    </tr>
    <tr v-for='item of data' :key='item.name'>
      <td>{{item.name}}</td>
      <td>{{item.dataType}}</td>
      <td v-if="item.elements!=''">
        {{item.example}}
        <table-layout :data='item.elements'></table-layout>
        <!-- <table v-if="item1.example">
                                <tr>
                                    <th>名称</th>
                                    <th>类型</th>
                                    <th>示例值</th>
                                    <th>描述</th>
                                </tr>
                                <tr v-for='item2 of item1.example' :key='item2.name'>
                                    <td>{{item2.name}}</td>
                                    <td>{{item2.type}}</td>
                                    <td>{{item2.example}}</td>
                                    <td>{{item2.description}}</td>
                                </tr>
                            </table> -->

      </td>
      <td v-else>{{item.example}}</td>
      <td>{{item.description}}
        <!-- <table-layout :data='item.elements'></table-layout> -->
      </td>
    </tr>
  </table>
</template>

<script>
  export default {
    name: "TableLayout",
    props: ["data"]
  };

</script>

<style scoped>
  table {
    padding: 10px 20px;
  }

  td,
  th {
    padding: 5px;
    min-height: 41px;
  }

  tr {
    height: 41px;
  }

  table,
  td,
  th {
    text-align: left;
    border: 1px solid #e9eaec;
    font-size: 12px;
  }

</style>
