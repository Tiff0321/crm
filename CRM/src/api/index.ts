export const fetchData = () => {
  return request({
    url: "https://www.fastmock.site/mock/dc695d037038802def4b989ba4650c3f/vms/getUser",
    method: "post",
  });
};

import request from "../utils/request";
export interface ApiResult<T> {
    code: number;
    message: string;
    data: T;
}
export async function GET<T>(url: string, params?: any): Promise<ApiResult<T>> {
    const response = await request.get<ApiResult<T>>(url, { params });
    return response.data;
}
export async function POST<T>(url: string, data?: any): Promise<ApiResult<T>> {
    const response = await request.post<ApiResult<T>>(url, data);
    return response.data;
}

export async function POST_FORM<T>(url: string, data?: any): Promise<ApiResult<T>> {
   const response = await request.post<ApiResult<T>>(url, data,{headers:{"Content-Type":"multipart/form-data"}});
  return response.data;
}

