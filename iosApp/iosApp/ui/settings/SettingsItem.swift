import SwiftUI

struct SettingsItem: View {
    
    let title: String
    let onItemClicked: () -> Void
    
    var body: some View {
        VStack {
            Text(title)
                .frame(maxWidth: .infinity, alignment: .leading)
                .foregroundColor(.white)
            Divider()
                .background(.gray)
        }
        .onTapGesture {
            onItemClicked()
        }
    }
}

struct SettingsItem_Previews: PreviewProvider {
    static var previews: some View {
        SettingsItem(title: "title", onItemClicked: {})
    }
}
