
// 修改默认错误提示
const dictionary = {
    zh_CN: {
        messages: {
            required: (field) => `${field}不能为空`,
            between: (field, data) => `${field}必须在${data[0]}~${data[1]}之间`,
            confirmed: (field, data) => `${field}必须与${data[0]}相匹配`,
            max: (field, data) => `${field}最大长度不能超过${data[0]}`,
            min: (field, data) => `${field}最小长度不能小于${data[0]}`,
            digits: (field, data) => `${field}必须为数字，且长度必须等于${data[0]}`,
            min_value: (field, data) => `${field}数值不能小于${data[0]}`,
            max_value: (field, data) => `${field}数值不能大于${data[0]}`,
            not_in: (field, data) => `${field}不能为${data.join(',')}`
        }
    }
}

VeeValidate.Validator.updateDictionary(dictionary);
Vue.use(VeeValidate, {
    locale: 'zh_CN',
    fieldsBagName: 'errorBag',
    events: 'blur|input'
})
var app = new Vue({
    el: '#registerForm',
    data:{
        loginName:'',
        email:'',
        password1:'',
        password2:'',
    }
})

