import SwiftUI
import shared

struct SplashScreen: View {
    private let navigate: (NavigationDirections) -> Void
    private let navigateBack: () -> Void
    
    private let viewModel: MainViewModel = MainDiHelper().getMainViewModel()
    @ObservedObject private var state: ObservableState<MainState>

    init(
        navigate: @escaping (NavigationDirections) -> Void,
        navigateBack: @escaping () -> Void
    ) {
        self.state = ObservableState<MainState>(value: viewModel.viewState.value as! MainState)
        self.navigate = navigate
        self.navigateBack = navigateBack
        observeState()
        observeNavigator()
    }
    
    private func observeState() {
        viewModel.viewState.collect(collector: Collector<MainState>{ state in
            self.state.value = state
            if(!state.isLoading) {
                navigate(state.directions)
            }
            
        }) { error in
            print("Error ocurred during state collection")
        }
        
    }
    
    private func observeNavigator() {
        viewModel.mainNavigator.commands.collect(collector: Collector<NavigationDirections>{
            command in onCommandReceived(command: command)
            
        }) { error in
            print("Error ocurred during navigator collection")
        }
    }
    
    private func onCommandReceived(command: NavigationDirections) {
        print(command.destination)
        if(command is NavigationDirections.NavigateUp){
            navigateBack()
        } else {
            navigate(command)
        }
     }
    
    var body: some View {
        
            VStack(alignment: .leading) {
                Color.black.ignoresSafeArea()
//                Image()
//                    .resizable()
//                    .scaledToFit()
//                    .frame(width: 300, height: 300)
        }
    }
}

