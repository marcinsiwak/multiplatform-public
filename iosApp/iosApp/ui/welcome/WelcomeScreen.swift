import SwiftUI
import shared

struct WelcomeScreen: View {
    @State private var login: String = ""
    @State private var password: String = ""
    @State private var authErrorMessage: String = ""
    
    let viewModel = WelcomeDiHelper().getViewModel()
    
    @ObservedObject private var state: ObservableState<WelcomeState>

    init() {
//        GoogleAuthOneTapConfiguration().setConfig()
        self.state = ObservableState<WelcomeState>(value: viewModel.viewState.value as! WelcomeState)
        observeState()
    }
    
    private func observeState() {
        viewModel.viewState.collect(collector: Collector<WelcomeState>{ state in
            self.state.value = state

        }) { error in
            print("Error ocurred during state collection")
        }
    }
    
    
    var body: some View {
        VStack(alignment: .center, spacing: 8) {
            InputView(value: $state.value.login, trailingIcon: {}, onValueChange: { value in
                viewModel.onLoginChanged(text: value)
            })
            InputView(value: $state.value.password, errorMessage: authErrorMessage, isPassword: true, trailingIcon: {}, onValueChange: { value in
                viewModel.onPasswordChanged(text: value)
            })

            Button(action: {
                viewModel.onLoginClicked()
            }) {
                Text(MR.strings().login.desc().localized())
            }
        
            Button(action: {
                loginUsingGoogle()
            }) {
                Text(MR.strings().welcome_google_login.desc().localized())
            }

            Text(MR.strings().welcome_no_account.desc().localized())
                .foregroundColor(Color.secondary)

            Button(action: {
                viewModel.onRegistrationClicked()
            }) {
                Text(MR.strings().welcome_create_account.desc().localized())
            }
        }
        .padding()
        .frame(minWidth: 0, maxWidth: .infinity, minHeight: 0, maxHeight: .infinity, alignment: .center)
        .background(Color.black)
    }
    
    func loginUsingGoogle() {
//        guard let presentingViewController = (UIApplication.shared.connectedScenes.first as? UIWindowScene)?.windows.first?.rootViewController else {return}
        
//        GIDSignIn.sharedInstance.signIn(withPresenting: presentingViewController) { result, error in
//            let user = result?.user
//            let idToken = user?.idToken?.tokenString
//            let accessToken = user?.accessToken.tokenString
//            if(idToken != nil) {
//                viewModel.onGoogleLogin(idToken: idToken!, accessToken: accessToken)
//            }
//        }
    }
}

struct WelcomeScreen_Previews: PreviewProvider {
    static var previews: some View {
        WelcomeScreen()
    }
}
