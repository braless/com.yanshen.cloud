package com.yanshen.controller;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.yanshen.common.Result;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Auther: @Yanchao
 * @Date: 2023-09-21 16:16
 * @Description:
 * @Location: com.yanshen.controller
 * @Project: com.yanshen.cloud
 */

@RestController
@RequestMapping("/sys-route")
public class SysRouteController {


    @RequestMapping("/list")
    public Result getAllRoute(){
        String routes="[\n" +
                "  {\n" +
                "    path: '/',\n" +
                "    name: 'Root',\n" +
                "    component: 'Layout',\n" +
                "    meta: {\n" +
                "      title: '首页',\n" +
                "      icon: 'home-2-line',\n" +
                "      breadcrumbHidden: true,\n" +
                "    },\n" +
                "    children: [\n" +
                "      {\n" +
                "        path: 'index',\n" +
                "        name: 'Index',\n" +
                "        component: '@/views/index',\n" +
                "        meta: {\n" +
                "          title: '首页',\n" +
                "          icon: 'home-2-line',\n" +
                "          noClosable: true,\n" +
                "        },\n" +
                "      },\n" +
                "    ],\n" +
                "  },\n" +
                "]";
        JSONObject obj =new JSONObject();
        obj.set("list",JSONUtil.parse(routes));
        return Result.success(obj);

    }


    @RequestMapping("/list2")
    public Result allRoute(){
        String r="[\n" +
                "  {\n" +
                "    path: '/',\n" +
                "    name: 'Root',\n" +
                "    component: 'Layout',\n" +
                "    meta: {\n" +
                "      title: '首页',\n" +
                "      icon: 'home-2-line',\n" +
                "      breadcrumbHidden: true,\n" +
                "    },\n" +
                "    children: [\n" +
                "      {\n" +
                "        path: 'index',\n" +
                "        name: 'Index',\n" +
                "        component: '@/views/index',\n" +
                "        meta: {\n" +
                "          title: '首页',\n" +
                "          icon: 'home-2-line',\n" +
                "          noClosable: true,\n" +
                "        },\n" +
                "      },\n" +
                "      {\n" +
                "        path: 'dashboard',\n" +
                "        name: 'Dashboard',\n" +
                "        component: '@/views/index/dashboard',\n" +
                "        meta: {\n" +
                "          title: '看板',\n" +
                "          icon: 'dashboard-line',\n" +
                "        },\n" +
                "      },\n" +
                "      {\n" +
                "        path: 'workbench',\n" +
                "        name: 'Workbench',\n" +
                "        component: '@/views/index/workbench',\n" +
                "        meta: {\n" +
                "          title: '工作台',\n" +
                "          icon: 'settings-6-line',\n" +
                "          dot: true,\n" +
                "        },\n" +
                "      },\n" +
                "    ],\n" +
                "  },\n" +
                "  {\n" +
                "    path: '/vab',\n" +
                "    name: 'Vab',\n" +
                "    component: 'Layout',\n" +
                "    meta: {\n" +
                "      title: '组件',\n" +
                "      icon: 'code-box-line',\n" +
                "    },\n" +
                "    children: [\n" +
                "      {\n" +
                "        path: 'icon',\n" +
                "        name: 'Icon',\n" +
                "        meta: {\n" +
                "          title: '图标',\n" +
                "          icon: 'remixicon-line',\n" +
                "        },\n" +
                "        children: [\n" +
                "          {\n" +
                "            path: 'remixIcon',\n" +
                "            name: 'RemixIcon',\n" +
                "            component: '@/views/vab/icon/remixIcon',\n" +
                "            meta: {\n" +
                "              title: '小清新图标',\n" +
                "            },\n" +
                "          },\n" +
                "          {\n" +
                "            path: 'iconSelector',\n" +
                "            name: 'IconSelector',\n" +
                "            component: '@/views/vab/icon/iconSelector',\n" +
                "            meta: {\n" +
                "              title: '图标选择器',\n" +
                "            },\n" +
                "          },\n" +
                "        ],\n" +
                "      },\n" +
                "      {\n" +
                "        path: 'permission',\n" +
                "        name: 'Permission',\n" +
                "        component: '@/views/vab/permission',\n" +
                "        meta: {\n" +
                "          title: '角色权限',\n" +
                "          icon: 'user-3-line',\n" +
                "          badge: 'Pro',\n" +
                "        },\n" +
                "      },\n" +
                "      {\n" +
                "        path: 'table',\n" +
                "        name: 'Table',\n" +
                "        meta: {\n" +
                "          title: '表格',\n" +
                "          guard: {\n" +
                "            role: ['Editor'],\n" +
                "            mode: 'except',\n" +
                "          },\n" +
                "          icon: 'table-2',\n" +
                "        },\n" +
                "        children: [\n" +
                "          {\n" +
                "            path: 'comprehensiveTable',\n" +
                "            name: 'ComprehensiveTable',\n" +
                "            component: '@/views/vab/table/comprehensiveTable',\n" +
                "            meta: {\n" +
                "              title: '综合表格',\n" +
                "            },\n" +
                "          },\n" +
                "          {\n" +
                "            path: 'detail',\n" +
                "            name: 'Detail',\n" +
                "            component: '@/views/vab/table/detail',\n" +
                "            meta: {\n" +
                "              hidden: true,\n" +
                "              title: '详情页',\n" +
                "              activeMenu: '/vab/table/comprehensiveTable',\n" +
                "              dynamicNewTab: true,\n" +
                "            },\n" +
                "          },\n" +
                "          {\n" +
                "            path: 'inlineEditTable',\n" +
                "            name: 'InlineEditTable',\n" +
                "            component: '@/views/vab/table/inlineEditTable',\n" +
                "            meta: {\n" +
                "              title: '行内编辑表格',\n" +
                "              noKeepAlive: true,\n" +
                "            },\n" +
                "          },\n" +
                "          {\n" +
                "            path: 'customTable',\n" +
                "            name: 'CustomTable',\n" +
                "            component: '@/views/vab/table/customTable',\n" +
                "            meta: {\n" +
                "              title: '自定义表格',\n" +
                "            },\n" +
                "          },\n" +
                "        ],\n" +
                "      },\n" +
                "      {\n" +
                "        path: 'list',\n" +
                "        name: 'List',\n" +
                "        component: '@/views/vab/list',\n" +
                "        meta: {\n" +
                "          title: '列表',\n" +
                "          guard: ['Admin'],\n" +
                "          icon: 'list-check-2',\n" +
                "        },\n" +
                "      },\n" +
                "      {\n" +
                "        path: 'form',\n" +
                "        name: 'Form',\n" +
                "        meta: {\n" +
                "          title: '表单',\n" +
                "          guard: ['Admin'],\n" +
                "          icon: 'file-list-2-line',\n" +
                "        },\n" +
                "        children: [\n" +
                "          {\n" +
                "            path: 'comprehensiveForm',\n" +
                "            name: 'ComprehensiveForm',\n" +
                "            component: '@/views/vab/form/comprehensiveForm',\n" +
                "            meta: {\n" +
                "              title: '综合表单',\n" +
                "            },\n" +
                "          },\n" +
                "          {\n" +
                "            path: 'stepForm',\n" +
                "            name: 'StepForm',\n" +
                "            component: '@/views/vab/form/stepForm',\n" +
                "            meta: {\n" +
                "              title: '分步表单',\n" +
                "            },\n" +
                "          },\n" +
                "          {\n" +
                "            path: 'button',\n" +
                "            name: 'Button',\n" +
                "            component: '@/views/vab/form/button',\n" +
                "            meta: {\n" +
                "              title: '按钮',\n" +
                "            },\n" +
                "          },\n" +
                "          {\n" +
                "            path: 'link',\n" +
                "            name: 'Link',\n" +
                "            component: '@/views/vab/form/link',\n" +
                "            meta: {\n" +
                "              title: '文字链接',\n" +
                "            },\n" +
                "          },\n" +
                "          {\n" +
                "            path: 'radio',\n" +
                "            name: 'Radio',\n" +
                "            component: '@/views/vab/form/radio',\n" +
                "            meta: {\n" +
                "              title: '单选框',\n" +
                "            },\n" +
                "          },\n" +
                "          {\n" +
                "            path: 'checkbox',\n" +
                "            name: 'Checkbox',\n" +
                "            component: '@/views/vab/form/checkbox',\n" +
                "            meta: {\n" +
                "              title: '多选框',\n" +
                "            },\n" +
                "          },\n" +
                "          {\n" +
                "            path: 'input',\n" +
                "            name: 'Input',\n" +
                "            component: '@/views/vab/form/input',\n" +
                "            meta: {\n" +
                "              title: '输入框',\n" +
                "            },\n" +
                "          },\n" +
                "          {\n" +
                "            path: 'inputNumber',\n" +
                "            name: 'InputNumber',\n" +
                "            component: '@/views/vab/form/inputNumber',\n" +
                "            meta: {\n" +
                "              title: '计数器',\n" +
                "            },\n" +
                "          },\n" +
                "          {\n" +
                "            path: 'select',\n" +
                "            name: 'Select',\n" +
                "            component: '@/views/vab/form/select',\n" +
                "            meta: {\n" +
                "              title: '选择器',\n" +
                "            },\n" +
                "          },\n" +
                "          {\n" +
                "            path: 'switch',\n" +
                "            name: 'Switch',\n" +
                "            component: '@/views/vab/form/switch',\n" +
                "            meta: {\n" +
                "              title: '开关',\n" +
                "            },\n" +
                "          },\n" +
                "          {\n" +
                "            path: 'slider',\n" +
                "            name: 'Slider',\n" +
                "            component: '@/views/vab/form/slider',\n" +
                "            meta: {\n" +
                "              title: '滑块',\n" +
                "            },\n" +
                "          },\n" +
                "          {\n" +
                "            path: 'timePicker',\n" +
                "            name: 'TimePicker',\n" +
                "            component: '@/views/vab/form/timePicker',\n" +
                "            meta: {\n" +
                "              title: '时间选择器',\n" +
                "            },\n" +
                "          },\n" +
                "          {\n" +
                "            path: 'datePicker',\n" +
                "            name: 'DatePicker',\n" +
                "            component: '@/views/vab/form/datePicker',\n" +
                "            meta: {\n" +
                "              title: '日期选择器',\n" +
                "            },\n" +
                "          },\n" +
                "          {\n" +
                "            path: 'dateTimePicker',\n" +
                "            name: 'DateTimePicker',\n" +
                "            component: '@/views/vab/form/dateTimePicker',\n" +
                "            meta: {\n" +
                "              title: '日期时间选择器',\n" +
                "            },\n" +
                "          },\n" +
                "          {\n" +
                "            path: 'rate',\n" +
                "            name: 'Rate',\n" +
                "            component: '@/views/vab/form/rate',\n" +
                "            meta: {\n" +
                "              title: '评分',\n" +
                "            },\n" +
                "          },\n" +
                "        ],\n" +
                "      },\n" +
                "    ],\n" +
                "  },\n" +
                "  {\n" +
                "    path: '/other',\n" +
                "    name: 'Other',\n" +
                "    component: 'Layout',\n" +
                "    meta: {\n" +
                "      title: '其他',\n" +
                "      icon: 'archive-line',\n" +
                "      guard: ['Admin'],\n" +
                "    },\n" +
                "    children: [\n" +
                "      {\n" +
                "        path: 'workflow',\n" +
                "        name: 'Workflow',\n" +
                "        component: '@/views/other/workflow',\n" +
                "        meta: {\n" +
                "          title: '工作流',\n" +
                "          guard: ['Admin'],\n" +
                "          icon: 'flow-chart',\n" +
                "        },\n" +
                "      },\n" +
                "      {\n" +
                "        path: 'echarts',\n" +
                "        name: 'Echarts',\n" +
                "        component: '@/views/other/echarts',\n" +
                "        meta: {\n" +
                "          title: '图表',\n" +
                "          guard: ['Admin'],\n" +
                "          icon: 'bubble-chart-line',\n" +
                "        },\n" +
                "      },\n" +
                "      {\n" +
                "        path: 'print',\n" +
                "        name: 'Print',\n" +
                "        component: '@/views/other/print',\n" +
                "        meta: {\n" +
                "          title: '打印',\n" +
                "          guard: ['Admin'],\n" +
                "          icon: 'printer-line',\n" +
                "        },\n" +
                "      },\n" +
                "      {\n" +
                "        path: 'notice',\n" +
                "        name: 'Notice',\n" +
                "        component: '@/views/other/notice',\n" +
                "        meta: {\n" +
                "          title: '通知',\n" +
                "          guard: ['Admin'],\n" +
                "          icon: 'message-2-line',\n" +
                "        },\n" +
                "      },\n" +
                "      {\n" +
                "        path: 'timeline',\n" +
                "        name: 'Timeline',\n" +
                "        component: '@/views/other/timeline',\n" +
                "        meta: {\n" +
                "          title: '时间线',\n" +
                "          guard: ['Admin'],\n" +
                "          icon: 'time-line',\n" +
                "        },\n" +
                "      },\n" +
                "      {\n" +
                "        path: 'tabs',\n" +
                "        name: 'Tabs',\n" +
                "        component: '@/views/other/tabs',\n" +
                "        meta: {\n" +
                "          title: '多标签',\n" +
                "          guard: ['Admin'],\n" +
                "          icon: 'bank-card-line',\n" +
                "        },\n" +
                "      },\n" +
                "      {\n" +
                "        path: 'dynamicMeta',\n" +
                "        name: 'DynamicMeta',\n" +
                "        component: '@/views/other/dynamicMeta',\n" +
                "        meta: {\n" +
                "          title: '动态Meta',\n" +
                "          guard: ['Admin'],\n" +
                "          icon: 'notification-badge-line',\n" +
                "          badge: '0',\n" +
                "        },\n" +
                "      },\n" +
                "      {\n" +
                "        path: 'dynamicSegment',\n" +
                "        name: 'DynamicSegment',\n" +
                "        redirect: '/other/dynamicSegment/test1/1',\n" +
                "        meta: {\n" +
                "          title: '动态路径参数',\n" +
                "          guard: ['Admin'],\n" +
                "          icon: 'arrow-left-right-line',\n" +
                "        },\n" +
                "        children: [\n" +
                "          {\n" +
                "            path: 'test1/:id',\n" +
                "            name: 'Test1',\n" +
                "            component: '@/views/other/dynamicSegment/test1',\n" +
                "            meta: {\n" +
                "              hidden: true,\n" +
                "              title: 'Params',\n" +
                "              dynamicNewTab: true,\n" +
                "            },\n" +
                "          },\n" +
                "          {\n" +
                "            path: 'test1/1',\n" +
                "            name: 'test1/1',\n" +
                "            component: '@/views/other/dynamicSegment/test1',\n" +
                "            meta: { title: 'Params id=1' },\n" +
                "          },\n" +
                "          {\n" +
                "            path: 'test2',\n" +
                "            name: 'Test2',\n" +
                "            component: '@/views/other/dynamicSegment/test2',\n" +
                "            meta: {\n" +
                "              hidden: true,\n" +
                "              title: 'Query',\n" +
                "              dynamicNewTab: true,\n" +
                "            },\n" +
                "          },\n" +
                "          {\n" +
                "            path: 'test2?id=1',\n" +
                "            name: 'test2?id=1',\n" +
                "            component: '@/views/other/dynamicSegment/test2',\n" +
                "            meta: { title: 'Query id=1' },\n" +
                "          },\n" +
                "        ],\n" +
                "      },\n" +
                "      {\n" +
                "        path: 'drag',\n" +
                "        name: 'Drag',\n" +
                "        meta: {\n" +
                "          title: '拖拽',\n" +
                "          guard: ['Admin'],\n" +
                "          icon: 'drag-drop-line',\n" +
                "        },\n" +
                "        children: [\n" +
                "          {\n" +
                "            path: 'dialogDrag',\n" +
                "            name: 'DialogDrag',\n" +
                "            component: '@/views/other/drag/dialogDrag',\n" +
                "            meta: {\n" +
                "              title: '弹窗拖拽',\n" +
                "            },\n" +
                "          },\n" +
                "          {\n" +
                "            path: 'cardDrag',\n" +
                "            name: 'CardDrag',\n" +
                "            component: '@/views/other/drag/cardDrag',\n" +
                "            meta: {\n" +
                "              title: '卡片拖拽',\n" +
                "            },\n" +
                "          },\n" +
                "          {\n" +
                "            path: 'flowSheetDrag',\n" +
                "            name: 'FlowSheetDrag',\n" +
                "            component: '@/views/other/drag/flowSheetDrag',\n" +
                "            meta: {\n" +
                "              title: '流程图拖拽',\n" +
                "              noKeepAlive: true,\n" +
                "            },\n" +
                "          },\n" +
                "        ],\n" +
                "      },\n" +
                "      {\n" +
                "        path: 'loading',\n" +
                "        name: 'Loading',\n" +
                "        component: '@/views/other/loading',\n" +
                "        meta: {\n" +
                "          title: '加载',\n" +
                "          guard: ['Admin'],\n" +
                "          icon: 'loader-line',\n" +
                "        },\n" +
                "      },\n" +
                "      {\n" +
                "        path: 'upload',\n" +
                "        name: 'Upload',\n" +
                "        component: '@/views/other/upload',\n" +
                "        meta: {\n" +
                "          title: '上传',\n" +
                "          guard: ['Admin'],\n" +
                "          icon: 'chat-upload-line',\n" +
                "        },\n" +
                "      },\n" +
                "      {\n" +
                "        path: 'menu1',\n" +
                "        name: 'Menu1',\n" +
                "        meta: {\n" +
                "          title: '多级路由缓存',\n" +
                "          guard: ['Admin'],\n" +
                "          icon: 'route-line',\n" +
                "        },\n" +
                "        children: [\n" +
                "          {\n" +
                "            path: 'menu1-1',\n" +
                "            name: 'Menu11',\n" +
                "            meta: {\n" +
                "              title: '多级路由1-1',\n" +
                "            },\n" +
                "            children: [\n" +
                "              {\n" +
                "                path: 'menu1-1-1',\n" +
                "                name: 'Menu111',\n" +
                "                meta: {\n" +
                "                  title: '多级路由1-1-1',\n" +
                "                },\n" +
                "                children: [\n" +
                "                  {\n" +
                "                    path: 'menu1-1-1-1',\n" +
                "                    name: 'Menu1111',\n" +
                "                    meta: {\n" +
                "                      title: '多级路由1-1-1-1',\n" +
                "                    },\n" +
                "                    component:\n" +
                "                      '@/views/other/nested/menu1/menu1-1/menu1-1-1/menu1-1-1-1',\n" +
                "                  },\n" +
                "                ],\n" +
                "              },\n" +
                "            ],\n" +
                "          },\n" +
                "        ],\n" +
                "      },\n" +
                "      {\n" +
                "        path: 'log',\n" +
                "        name: 'Log',\n" +
                "        component: '@/views/other/errorLog',\n" +
                "        meta: {\n" +
                "          title: '错误日志模拟',\n" +
                "          guard: ['Admin'],\n" +
                "          icon: 'error-warning-line',\n" +
                "        },\n" +
                "      },\n" +
                "      {\n" +
                "        path: 'cssfx',\n" +
                "        name: 'Cssfx',\n" +
                "        component: '@/views/other/cssfx',\n" +
                "        meta: {\n" +
                "          title: 'Css动画',\n" +
                "          guard: ['Admin'],\n" +
                "          icon: 'css3-line',\n" +
                "        },\n" +
                "      },\n" +
                "      {\n" +
                "        path: 'social',\n" +
                "        name: 'Social',\n" +
                "        component: '@/views/other/social',\n" +
                "        meta: {\n" +
                "          title: '第三方登录',\n" +
                "          guard: ['Admin'],\n" +
                "          icon: 'github-fill',\n" +
                "        },\n" +
                "      },\n" +
                "      {\n" +
                "        path: '//github.com/chuzhixin/vue-admin-beautiful?utm_source=gold_browser_extension',\n" +
                "        name: 'ExternalLink',\n" +
                "        meta: {\n" +
                "          title: '外链',\n" +
                "          target: '_blank',\n" +
                "          guard: {\n" +
                "            role: ['Admin', 'Editor'],\n" +
                "            mode: 'oneOf',\n" +
                "          },\n" +
                "          icon: 'external-link-line',\n" +
                "        },\n" +
                "      },\n" +
                "      {\n" +
                "        path: 'iframe',\n" +
                "        name: 'Iframe',\n" +
                "        redirect: '/other/iframe/search',\n" +
                "        meta: {\n" +
                "          title: 'Iframe',\n" +
                "          guard: ['Admin'],\n" +
                "          icon: 'window-line',\n" +
                "        },\n" +
                "        children: [\n" +
                "          {\n" +
                "            path: 'view',\n" +
                "            name: 'IframeView',\n" +
                "            component: '@/views/other/iframe/view',\n" +
                "            meta: {\n" +
                "              hidden: true,\n" +
                "              title: 'Iframe',\n" +
                "              icon: 'window-line',\n" +
                "              dynamicNewTab: true,\n" +
                "            },\n" +
                "          },\n" +
                "          {\n" +
                "            path: 'view?url=www.so.com&title=360%E6%90%9C%E7%B4%A2&icon=search-2-line',\n" +
                "            name: 'Search360Iframe',\n" +
                "            meta: { title: '360搜索', icon: 'search-2-line' },\n" +
                "          },\n" +
                "          {\n" +
                "            path: 'view?url=www.bilibili.com&title=%E5%93%94%E5%93%A9%E5%93%94%E5%93%A9&icon=bilibili-line',\n" +
                "            name: 'BiliBiliIframe',\n" +
                "            meta: { title: '哔哩哔哩', icon: 'bilibili-line' },\n" +
                "          },\n" +
                "          {\n" +
                "            path: 'search',\n" +
                "            name: 'IframeSearch',\n" +
                "            component: '@/views/other/iframe/search',\n" +
                "            meta: {\n" +
                "              title: '自定义Iframe',\n" +
                "              icon: 'search-2-line',\n" +
                "            },\n" +
                "          },\n" +
                "        ],\n" +
                "      },\n" +
                "      {\n" +
                "        path: 'excel',\n" +
                "        name: 'Excel',\n" +
                "        meta: {\n" +
                "          title: 'Excel',\n" +
                "          guard: ['Admin'],\n" +
                "          icon: 'file-excel-2-line',\n" +
                "        },\n" +
                "        children: [\n" +
                "          {\n" +
                "            path: 'exportExcel',\n" +
                "            name: 'ExportExcel',\n" +
                "            component: '@/views/other/excel/exportExcel',\n" +
                "            meta: {\n" +
                "              title: '导出Excel',\n" +
                "            },\n" +
                "          },\n" +
                "          {\n" +
                "            path: 'exportSelectedExcel',\n" +
                "            name: 'SelectExcel',\n" +
                "            component: '@/views/other/excel/exportSelectExcel',\n" +
                "            meta: {\n" +
                "              title: '导出选中行Excel',\n" +
                "            },\n" +
                "          },\n" +
                "          {\n" +
                "            path: 'exportMergeHeaderExcel',\n" +
                "            name: 'MergeHeaderExcel',\n" +
                "            component: '@/views/other/excel/exportMergeHeaderExcel',\n" +
                "            meta: {\n" +
                "              title: '导出合并Excel',\n" +
                "            },\n" +
                "          },\n" +
                "        ],\n" +
                "      },\n" +
                "    ],\n" +
                "  },\n" +
                "  {\n" +
                "    path: '/mall',\n" +
                "    name: 'Mall',\n" +
                "    component: 'Layout',\n" +
                "    meta: {\n" +
                "      title: '物料源',\n" +
                "      icon: 'apps-line',\n" +
                "      levelHidden: true,\n" +
                "      guard: ['Admin'],\n" +
                "    },\n" +
                "    children: [\n" +
                "      {\n" +
                "        path: 'goods',\n" +
                "        name: 'Goods',\n" +
                "        component: '@/views/mall/goods',\n" +
                "        meta: {\n" +
                "          title: '物料市场',\n" +
                "          icon: 'shopping-cart-line',\n" +
                "          badge: 'Hot',\n" +
                "        },\n" +
                "      },\n" +
                "    ],\n" +
                "  },\n" +
                "  {\n" +
                "    path: '/noColumn',\n" +
                "    name: 'NoColumn',\n" +
                "    component: 'Layout',\n" +
                "    meta: {\n" +
                "      title: '无分栏',\n" +
                "      icon: 'delete-column',\n" +
                "      guard: ['Admin'],\n" +
                "      breadcrumbHidden: true,\n" +
                "    },\n" +
                "    children: [\n" +
                "      {\n" +
                "        path: 'deleteColumn',\n" +
                "        name: 'DeleteColumn',\n" +
                "        component: '@/views/noColumn/deleteColumn',\n" +
                "        meta: {\n" +
                "          title: '无分栏',\n" +
                "          icon: 'delete-column',\n" +
                "          noColumn: true,\n" +
                "        },\n" +
                "      },\n" +
                "    ],\n" +
                "  },\n" +
                "  {\n" +
                "    path: '/setting',\n" +
                "    name: 'PersonnelManagement',\n" +
                "    component: 'Layout',\n" +
                "    meta: {\n" +
                "      title: '配置',\n" +
                "      icon: 'user-settings-line',\n" +
                "      guard: ['Admin'],\n" +
                "    },\n" +
                "    children: [\n" +
                "      {\n" +
                "        path: 'personalCenter',\n" +
                "        name: 'PersonalCenter',\n" +
                "        component: '@/views/setting/personalCenter',\n" +
                "        meta: {\n" +
                "          title: '个人中心',\n" +
                "          icon: 'map-pin-user-line',\n" +
                "        },\n" +
                "      },\n" +
                "      {\n" +
                "        path: 'userManagement',\n" +
                "        name: 'UserManagement',\n" +
                "        component: '@/views/setting/userManagement',\n" +
                "        meta: {\n" +
                "          title: '用户管理',\n" +
                "          icon: 'user-3-line',\n" +
                "        },\n" +
                "      },\n" +
                "      {\n" +
                "        path: 'roleManagement',\n" +
                "        name: 'RoleManagement',\n" +
                "        component: '@/views/setting/roleManagement',\n" +
                "        meta: {\n" +
                "          title: '角色管理',\n" +
                "          icon: 'admin-line',\n" +
                "        },\n" +
                "      },\n" +
                "      {\n" +
                "        path: 'departmentManagement',\n" +
                "        name: 'DepartmentManagement',\n" +
                "        component: '@/views/setting/departmentManagement',\n" +
                "        meta: {\n" +
                "          title: '部门管理',\n" +
                "          icon: 'group-line',\n" +
                "        },\n" +
                "      },\n" +
                "      {\n" +
                "        path: 'menuManagement',\n" +
                "        name: 'MenuManagement',\n" +
                "        component: '@/views/setting/menuManagement',\n" +
                "        meta: {\n" +
                "          title: '菜单管理',\n" +
                "          icon: 'menu-2-fill',\n" +
                "        },\n" +
                "      },\n" +
                "      {\n" +
                "        path: 'systemLog',\n" +
                "        name: 'SystemLog',\n" +
                "        component: '@/views/setting/systemLog',\n" +
                "        meta: {\n" +
                "          title: '系统日志',\n" +
                "          icon: 'file-shield-2-line',\n" +
                "        },\n" +
                "      },\n" +
                "    ],\n" +
                "  },\n" +
                "  {\n" +
                "    path: '/error',\n" +
                "    name: 'Error',\n" +
                "    component: 'Layout',\n" +
                "    meta: {\n" +
                "      title: '错误页',\n" +
                "      icon: 'error-warning-line',\n" +
                "      levelHidden: true,\n" +
                "    },\n" +
                "    children: [\n" +
                "      {\n" +
                "        path: '403',\n" +
                "        name: 'Error403',\n" +
                "        component: '@/views/403',\n" +
                "        meta: {\n" +
                "          title: '403',\n" +
                "          icon: 'error-warning-line',\n" +
                "        },\n" +
                "      },\n" +
                "      {\n" +
                "        path: '404',\n" +
                "        name: 'Error404',\n" +
                "        component: '@/views/404',\n" +
                "        meta: {\n" +
                "          title: '404',\n" +
                "          icon: 'error-warning-line',\n" +
                "        },\n" +
                "      },\n" +
                "    ],\n" +
                "  },\n" +
                "]\n";
        JSONObject obj =new JSONObject();
        obj.set("list",JSONUtil.parse(r));
        return Result.success(obj);
    }
}
