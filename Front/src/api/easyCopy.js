import axios from '@/libs/api.request'
import easyCopy  from "@/components/easyCopy";

const basePath = '/api'

//解析url获取新闻数据 /easyCopy/copy
export const copy = (data) => {
    return axios.request({
        url: `${basePath}/easyCopy/copy`,
        params: data,
        method: 'get'
    })
}
