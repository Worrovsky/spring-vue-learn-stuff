# Real world app example

## Что интересного

Структура файлов:

- `components` - переиспользуемые компоненты
- `views` - отдельные страницы

Разное

- Имена файлов компонентов, страниц с заглавной буквы
- Использование `@` (алиас для `/src`) при импорте: например `import Home from '@/views/About.vue'`
- ленивая загрузка в роутере `component: () => import(''@/views/Home.vue)`
- Компоненты и страницы именуем с префиксом, чтобы отличать собственные от заимствованных из сторонних библиотек. Например по первым символам собственного приложения и библиотек или по префиксу `App` для собственных

Страницы и роутер:

    {
      path: '/register',
      name: 'register',
      component: () => import('@/views/Register.vue'),
    },

- обязательно используем `name`
  - линки только через `name`, не через `path`
- имя со строчной

## Vuetify

Основа

    <v-app>
      <v-main>
        <v-container>

        </v-container>
      </v-main>
    <v-app>

Внутри контейнера обычно строки и колонки

    <v-container>
      <v-row>
        <v-col>
        </v-col>
      </v-row>
    </v-container>

## Vuex

### Основы

Глобально хранит и изменяет состояние всего приложения

Создаем переменные в области `state`

Определяем мутации для них

Используем в компонентах,создавая computed-свойства (удобнее так)

### Модули

Для разделения переменных по областям использовать модули

Структура папок:

    src
      `--store
          |--modules
          |   `--auth.js
          `--index.js

Содержимое модуля - это состояние, мутации и т.п. как для общего хранилища и их импорт

    //файл scr/store/modules/auth.js
    const state = {
      a: 1
    }
    const mutations = {
      // ...
    }
    export default {
      state,
      mutation
    }

Далее модуль импортируем в основной объект хранилища

    //файл src/store/index.js
    ...
    import auth from '@/store/index.js'
    ...
    export default new Vuex.Store({
      ...
      modules: {
        auth
      }

Теперь можно использовать в компонентах

Обращение к переменной через префикс модуля `this.$store.state.auth.isSubmitting`

Вызов мутации обычным способом `this.$store.commit('registerStart')`

### Actions

По сути те же мутации, но внутри обычно выполняются асинхронные операции (api-запросы, работа с БД)

    ...
    actions: {
      someAction(context) {
        // context - это само хранилище (this.$store),поэтому можно напр.
        context.commit('registerStart')
      }
    }

Вызов через метод `dispatch` `this.$store.dispatch('someAction')`

## Схема взаимодействия с API с помощью axios

- Создаем прослойку для axios (для конкретной настройки: например base url)
- На каждую сущность создаем свой js файл с запросами. В этих запросах используется объект axios из прослойки

Структура файлов

    src
        |...
        `api
            |--axios.js
            `--auth.js

- Запрос - просто вызов нужного метода у `axios`, например `axios.post('/users', credentials)`
- Этот запрос используем в action хранилища (модули используем) через **Promise**

Например:

    const actions = {
        register(context, creds) {
            return new Promise(() = > {
                authApi.register()
                .then(...)
                .catch(...)
            })
        }
    }

- Для отслеживвания состояния запроса (например для изменения доступности кнопки на период запроса) создаем мутации по схеме `xxxStart`, `xxxSuccess`, `xxxFailure` и используем их на этапах обработки запроса. См. пример с `isSubmitting` в файле `@/store/module/auth.js`

- Передача результата запроса: стандартными средствами **Promise** через функцию `resolve` 

Пример

    //файл модуля store, метод actions
    ...
      return new Promise((resolve) => {
        someApi.someApiMethod()
          .then((response) => {
            ...
            resolve(response.data.<что-то из данных>)
            })
      }

      //файл компонента
      ...
        this.$store.dispatch(...)
          .then((<данные, переданные через resolve>) => {...})


## Конвенция именования мутаций и действий Vuex

Проблемы:

* Мутации и действия имеют глобальную область видимости, поэтому нельзя одинаковое наименование использовать в разных модулях
* Вызов мутаций и действий выполняется по строковым именам: возможны ошибки, опечатки

Предлагается такая техника (что-то аналогичное в Redux):

* В модуле, где определены мутации/действия, создаем объект `mutationTypes` с перечислением зарегистрированных мутаций и их псевдонимов, и экспортируем его. Псевдонимы задаем с префиксами

Например:

    //файл модуля vuex (напр. /store/modules/auth.js)
    export const mutationTypes = {
      registerStart: '[auth] registerStart',
      ...
    }

* Добавляем в само определение мутаций использование объекта с псевдонимами

Вот пример. Здесь `[]` используются для вычисления выражения (?? не очень понятно)

    //файл модуля vuex (напр. /store/modules/auth.js)
    const mutations = {
      [mutationTypes.registerStart](state) {
        state.isSubmitting = false
      }
    }

* И теперь вызов мутации/действия тоже через пcевдонимы

Например в том же модуле

    //файл модуля vuex (напр. /store/modules/auth.js)
    ...
    context.commit(mutationTypes.registerStart)

Или в компоненте (внимание на импорт через {})

    //файл компонента
    import {actionTypes} from '@/store/modules/auth'
    ...
      onSubmit() {
        this.$store.dispatch(actionTypes.register)
      }

## Использование mapState

Пример. Обращать внимание на spread-оператор (...) 

    import {mapState} from 'vuex'
    ...
      computed: {
        ...mapState({
          isSubmitting: state => state.auth.isSubmitting,
          ... 
        })
      }

* немного сокращает запись
* группирует вычисляемые свойства, связанные с хранилищем, в одном месте, что упрощает сопровождение

## Геттеры в Vuex

Когда нужно получить значения на основе свойств хранилища или просто получить эти свойства. Аналог `computed`.

    // файл модуля хранилища (напр. @/store/modules/auth)
    const getters = {
      currentUser: state => {
        return state.currentUser // здесь нет префиксов, видим то, что есть в хранилище "локальном"
      }
    }
    ... 
    export default {
      ...,
      getters
    }

Можно применять ту же технику с `getterTypes` как для мутаций и действий

Вызывать можно через `$store.getters.currentUser` или через хелпер `mapGetters`(объект, ключ - имя свойства в компоненте, значение - имя геттера, заданное в хранилище)

    // файл компонента, где нужно вызвать геттер
    import {mapGetters} from 'vuex'
    iimport {getterTypes} from '@/store/modules/auth'
    ...
    computed: {
      ...mapGetters({
        currentUser: getterTypes.currentUser,
      })
    }
  
