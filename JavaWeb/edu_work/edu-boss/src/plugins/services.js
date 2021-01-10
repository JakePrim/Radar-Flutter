import { TokenService, OptionService, CourseService, ContentService, UserService } from '../services'

export default Vue => {
  // alias
  const services = {
    token: TokenService,
    option: OptionService,
    course: CourseService,
    content: ContentService,
    user: UserService
  }

  // mount the services to Vue
  Object.defineProperties(Vue, {
    services: { get: () => services }
  })

  // mount the services to Vue component instance
  Object.defineProperties(Vue.prototype, {
    $services: { get: () => services }
  })
}
