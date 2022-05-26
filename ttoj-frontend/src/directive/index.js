import directive from './directives'

const importDirective = Vue => {
   Vue.directive('focus', directive.focus)
}

export default importDirective
